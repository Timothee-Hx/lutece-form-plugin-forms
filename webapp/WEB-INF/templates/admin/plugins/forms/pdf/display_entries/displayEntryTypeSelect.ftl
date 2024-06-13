<#--
Macro: displayEntryTypeSelect
Description: Display an entry of type select
Parameters: entry, list_responses
-->
<#macro displayEntryTypeSelect entry, list_responses >
			<#if list_responses?has_content>
				<#assign iteration = 0>
				<#list list_responses as response>
					<#if iteration != 0 && response.field??>
						<span>; </span>
					</#if>
					<#assign iteration = iteration + 1>
					<span><#if response.field??>${response.field.title!''}</#if></span>
				</#list>
			</#if>
</#macro>
