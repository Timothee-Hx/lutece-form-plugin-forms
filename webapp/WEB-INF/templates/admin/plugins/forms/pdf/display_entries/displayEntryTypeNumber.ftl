<#--
Macro: displayEntryTypeNumber
Description: Display an entry of type number
Parameters: entry, list_responses
-->
<#macro displayEntryTypeNumber entry, list_responses >
			<#if list_responses?has_content>
				<#assign iteration = 0>
				<#list list_responses as response>
					<#if iteration != 0 && response.toStringValueResponse?has_content>
						<span>; </span>
					</#if>
					<#assign iteration = iteration + 1>
						<span>${response.toStringValueResponse!''}</span>
				</#list>
			</#if>
</#macro>
