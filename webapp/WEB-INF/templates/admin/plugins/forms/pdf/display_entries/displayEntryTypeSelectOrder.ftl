<#--
Macro: displayEntryTypeSelectOrder
Description: Display an entry of type select order
Parameters: entry, list_responses
Entry not found in basic config of forms entrytype, not tested
-->
<#macro displayEntryTypeSelectOrder entry, list_responses >
			<#if list_responses?has_content>
				<#assign iteration = 0>
				<#list list_responses?sort_by('sortOrder') as response>
					<#if iteration != 0 && response.field??>
						<span>; </span>
					</#if>
					<#assign iteration = iteration + 1>
					<span>
						<#if response.field??>${response.sortOrder}: ${response.field.title!''}</#if>
					</span>
				</#list>
			</#if>
</#macro>
