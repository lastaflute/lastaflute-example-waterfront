package org.docksidestage.app.web.withdrawal;

import jakarta.annotation.Resource;

import org.docksidestage.app.web.base.WaterfrontBaseAction;
import org.docksidestage.app.web.signout.SignoutAction;
import org.docksidestage.dbflute.exbhv.MemberBhv;
import org.docksidestage.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dbflute.exentity.Member;
import org.docksidestage.dbflute.exentity.MemberWithdrawal;
import org.lastaflute.core.time.TimeManager;
import org.lastaflute.web.Execute;
import org.lastaflute.web.response.HtmlResponse;

/**
 * @author annie_pocket
 * @author jflute
 */
public class WithdrawalAction extends WaterfrontBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private TimeManager timeManager;
    @Resource
    private MemberBhv memberBhv;
    @Resource
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Execute
    public HtmlResponse index() {
        return asHtml(path_Withdrawal_WithdrawalJsp).useForm(WithdrawalForm.class);
    }

    @Execute
    public HtmlResponse confirm(WithdrawalForm form) {
        validate(form, messages -> {}, () -> {
            return asHtml(path_Withdrawal_WithdrawalJsp);
        });
        return asHtml(path_Withdrawal_WithdrawalConfirmJsp);
    }

    @Execute
    public HtmlResponse done(WithdrawalForm form) {
        Integer memberId = getUserBean().get().getMemberId();

        MemberWithdrawal withdrawal = new MemberWithdrawal();
        withdrawal.setMemberId(memberId);
        withdrawal.setWithdrawalReasonCodeAsWithdrawalReason(form.reasonCode);
        withdrawal.setWithdrawalReasonInputText(form.reasonInput);
        withdrawal.setWithdrawalDatetime(timeManager.currentDateTime());
        memberWithdrawalBhv.insert(withdrawal);

        // update status of member
        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberStatusCode_Withdrawal();
        memberBhv.updateNonstrict(member);

        return redirect(SignoutAction.class);
    }
}
