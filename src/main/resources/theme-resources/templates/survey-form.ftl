<#import "template.ftl" as layout>
<#import "buttons.ftl" as buttons>

<@layout.registrationLayout displayMessage=false; section>
    <!-- template: survey-form.ftl -->

    <#if section = "header">
        ${msg("userSurvey")}
    <#elseif section = "form">
        <form class="${properties.kcFormClass!}" action="${url.loginAction}" method="POST">
            <div class="${properties.kcContentWrapperClass}">
                <label for="surveyInformation">Enter your survey information below</label>:<br/>
                <textarea
                        aria-label="survey information"
                        class="${properties.kcInputClass!}"
                        id="surveyInformation"
                        name="surveyInformation"
                        rows="5"
                        required>
            </textarea>
            </div>
            <@buttons.actionGroup horizontal=true>
                <@buttons.button name="accept" id="kc-accept" label="doAccept" class=["kcButtonPrimaryClass"]/>
                <@buttons.button name="cancel" id="kc-decline" label="doDecline" class=["kcButtonSecondaryClass"]/>
            </@buttons.actionGroup>
        </form>
        <div class="clearfix"></div>
    </#if>
</@layout.registrationLayout>
