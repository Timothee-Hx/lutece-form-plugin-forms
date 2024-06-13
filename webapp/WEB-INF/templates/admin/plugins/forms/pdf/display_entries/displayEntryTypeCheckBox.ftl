<#--
Macro: displayEntryTypeCheckBox
Description: Display an entry of type Check Box
Parameters: entry, list_responses
-->
<#macro displayEntryTypeCheckBox entry, list_responses >
			<#if list_responses?has_content>
				<#assign iteration = 0>
				<#list list_responses as response>
					<#if iteration != 0 && response.file.title?has_content>
						<span>; </span>
					</#if>
					<#assign iteration = iteration + 1>
					<span>
						<#if response.field??>${response.field.title!''}</#if>
					</span>
				</#list>
			</#if>
</#macro>
