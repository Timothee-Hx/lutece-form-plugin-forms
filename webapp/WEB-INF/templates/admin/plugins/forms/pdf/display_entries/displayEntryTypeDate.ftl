<#--
Macro: displayEntryTypeDate
Description: Display an entry of type date
Parameters: entry, list_responses
-->
<#macro displayEntryTypeDate entry, list_responses >
			<#if list_responses?has_content>
				<#assign iteration = 0>
				<#list list_responses as response>
					<#if response.responseValue??>
						<#if iteration != 0 && response.file.title?has_content>
							<span>; </span>
						</#if>
						<#assign iteration = iteration + 1>
						<span>${response.responseValue?number?number_to_date}</span>
					</#if>
				</#list>
			</#if>
</#macro>
