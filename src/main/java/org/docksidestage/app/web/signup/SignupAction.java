package org.docksidestage.app.web.signup;

import java.util.Random;

import jakarta.annotation.Resource;

import org.docksidestage.app.web.base.WaterfrontBaseAction;
import org.docksidestage.app.web.base.login.WaterfrontLoginAssist;
import org.docksidestage.app.web.mypage.MypageAction;
import org.docksidestage.dbflute.exbhv.MemberBhv;
import org.docksidestage.dbflute.exbhv.MemberSecurityBhv;
import org.docksidestage.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dbflute.exentity.Member;
import org.docksidestage.dbflute.exentity.MemberSecurity;
import org.docksidestage.dbflute.exentity.MemberService;
import org.docksidestage.mylasta.direction.WaterfrontConfig;
import org.docksidestage.mylasta.mail.member.WelcomeMemberPostcard;
import org.lastaflute.core.mail.Postbox;
import org.lastaflute.core.security.PrimaryCipher;
import org.lastaflute.web.Execute;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author annie_pocket
 * @author jflute
 */
public class SignupAction extends WaterfrontBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Resource
    private MemberBhv memberBhv;
    @Resource
    private MemberSecurityBhv memberSecurityBhv;
    @Resource
    private MemberServiceBhv memberServiceBhv;
    @Resource
    private WaterfrontLoginAssist waterfrontLoginAssist;
    @Resource
    private Postbox postbox;
    @Resource
    private WaterfrontConfig waterfrontConfig;
    @Resource
    private PrimaryCipher primaryCipher;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index() {
        return asHtml(path_Signup_SignupJsp).useForm(SignupAction.class);
    }

    @Execute
    public HtmlResponse signup(SignupForm form) {
        validate(form, messages -> {
            int count = memberBhv.selectCount(cb -> {
                cb.query().setMemberAccount_Equal(form.memberAccount);
            });
            if (count > 0) {
                messages.addErrorsSignupAccountAlreadyExists("account");
            }
        } , () -> {
            return asHtml(path_Signup_SignupJsp);
        });
        Integer memberId = newMember(form);
        waterfrontLoginAssist.identityLogin(memberId.longValue(), op -> {}); // no remember-me here

        WelcomeMemberPostcard.droppedInto(postbox, postcard -> {
            postcard.setFrom(waterfrontConfig.getMailAddressSupport(), "Waterfront Support");
            postcard.addTo(deriveMemberMailAddress(form));
            postcard.setDomain(waterfrontConfig.getServerDomain());
            postcard.setMemberName(form.memberName);
            postcard.setToken(generateToken());
        });
        return redirect(MypageAction.class);
    }

    private String deriveMemberMailAddress(SignupForm form) {
        return form.memberAccount + "@docksidestage.org"; // #simple_for_example
    }

    private String generateToken() {
        return primaryCipher.encrypt(String.valueOf(new Random().nextInt())); // #simple_for_example
    }

    @Execute
    public HtmlResponse register() {
        return asHtml(path_Signup_SignupJsp).useForm(SignupAction.class);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private Integer newMember(SignupForm form) {
        Member member = new Member();
        member.setMemberName(form.memberName);
        member.setMemberAccount(form.memberAccount);
        member.setMemberStatusCode_Provisional();
        memberBhv.insert(member);

        MemberSecurity security = new MemberSecurity();
        security.setMemberId(member.getMemberId());
        security.setLoginPassword(waterfrontLoginAssist.encryptPassword(form.password));
        security.setReminderQuestion(form.reminderQuestion);
        security.setReminderAnswer(form.reminderAnswer);
        security.setReminderUseCount(0);
        memberSecurityBhv.insert(security);

        MemberService service = new MemberService();
        service.setMemberId(member.getMemberId());
        service.setServicePointCount(0);
        service.setServiceRankCode_Plastic();
        memberServiceBhv.insert(service);
        return member.getMemberId();
    }
}
