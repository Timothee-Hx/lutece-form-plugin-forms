<#--
Macro: displayEntryTypeTextArea
Description: Display an entry of type text area
Parameters: entry, list_responses
-->
<#macro displayEntryTypeTextArea entry, list_responses >
    <#assign iteration = 0>
		<#if list_responses?has_content>
            <#if iteration != 0 && response.toStringValueResponse?has_content>
                <span>; </span>
            </#if>
            <#assign iteration = iteration + 1>
				<#list list_responses as response>
					${response.toStringValueResponse!?replace("\n", "<br>")}
				</#list>
			</#if>
</#macro>
