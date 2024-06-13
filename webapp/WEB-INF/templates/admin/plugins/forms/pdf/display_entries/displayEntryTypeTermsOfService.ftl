<#--
Macro: displayEntryTypeTermsOfService
Description: Display an entry of type "Terms of Service" with a link to the accepted terms of service
Parameters: entry, list_responses
-->
<#macro displayEntryTypeTermsOfService entry, list_responses >
	<div style="display: flex; flex-wrap: wrap;">
		<div style="flex: 0 0 75%; max-width: 75%;">
			<#if getResponseContainingTheFieldWithCode( list_responses, "tos" )?? >
				<#assign responseTos = getResponseContainingTheFieldWithCode( list_responses, "tos" ) >
			</#if>
			<#if getResponseContainingTheFieldWithCode( list_responses, "agreement" )?? >
				<#assign responseAgreement = getResponseContainingTheFieldWithCode( list_responses, "agreement" ) >
			</#if>
			<#if responseAgreement?? && responseAgreement.toStringValueResponse == "true">
				<@link href='${base_url!""}jsp/site/RunStandaloneApp.jsp?page=formsTermsOfService&view=acceptedTermsOfService&id_response=${(responseTos.idResponse)!}' params='onclick="javascript:openFormsTermsOfService(this.href); return false;" target="_blank"'>#i18n{forms.entryType.termsOfService.readOnly.link}</@link>
			<#else>
				<span style="background-color: #f8d7da; color: #721c24; padding: .25em .4em; font-size: 75%; font-weight: 700; line-height: 1; text-align: center; white-space: nowrap; vertical-align: baseline; border-radius: .25rem;">#i18n{forms.entryType.termsOfService.readOnly.unknown}</span>
			</#if>
		</div>
	</div>
</#macro>
