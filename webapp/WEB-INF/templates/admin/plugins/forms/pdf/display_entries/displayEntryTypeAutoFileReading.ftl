<#--
Macro: displayEntryTypeAutoFileReading
Description: Display an entry of type array
Parameters: entry, list_responses
Entry not found in basic config of forms entrytype, not tested
-->
<#macro displayEntryTypeAutoFileReading entry, list_responses >
	<div style="display: flex; flex-wrap: wrap;">
		<div style="flex: 0 0 25%; max-width: 25%;"><p style="text-align: right; font-weight: bold;">${entry.title!''}</p></div>
		<div style="flex: 0 0 75%; max-width: 75%;">
			<#if list_responses?has_content>
				<#list list_responses as response>
					<p>
						<#if response.file?exists>
							<@link href='${base_url!""}jsp/admin/plugins/forms/FormsDownloadFile.jsp?id_file=${response.file.idFile}&id_response=${response.idResponse}'>
								${response.file.title}(${response.file.size} O)
							</@link>
						</#if>
					</p>
				</#list>
			</#if>
		</div>
	</div>
</#macro>
