<#--
Macro: displayEntryTypeRadioButton
Description: Display an entry of type radio button
Parameters: entry, list_responses
-->
<#macro displayEntryTypeRadioButton entry, list_responses >
			<#if list_responses?has_content>
				<#assign iteration = 0>
				<#list list_responses as response>
					<#if iteration != 0>
						<span>; </span>
					</#if>
					<#assign iteration = iteration + 1>
					<span>
						<#if response.field??>${response.field.title!''}
							<#if response.field.fileImage.url??>
								<@img url=response.field.fileImage.url title=response.field.title alt=response.field.title params='width="100" height="100"' />
							</#if>
						</#if>
					</span>
				</#list>
			</#if>
</#macro>
