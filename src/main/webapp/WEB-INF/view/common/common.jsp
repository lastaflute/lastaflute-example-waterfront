<%@page pageEncoding="UTF-8"%>

<%-- JSTL tag libraries --%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- LastaFlute tag libraries --%>
<%@taglib prefix="la" uri="http://lastaflute.org/latags" %>
<%@taglib prefix="f" uri="http://lastaflute.org/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="viewPrefix" value="/WEB-INF/view" />
<c:set var="vq" value="?v=${f:version()}" />
