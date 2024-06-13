<#--
Macro: displayEntryTypeCamera
Description: Display an entry of type camera
Parameters: entry, list_responses
-->
<#macro displayEntryTypeCamera entry, list_responses >
	<div style="display: flex; flex-wrap: wrap;">
		<div style="flex: 0 0 75%; max-width: 75%;">
			<#if list_responses?has_content>
				<#list list_responses as response>
					<p>
						<#assign displayImageWithBase64 = false>
						<#if base64?exists && base64?is_boolean>
							<#assign displayImageWithBase64 = base64>
						</#if>
						<#if displayImageWithBase64>
							<#if response.file?exists && entry?exists >
								<img src="data:image/jpeg;base64,${response.toStringValueResponse!}" width="100px" height="100px"/>
							</#if>
						<#else>
							<#if response.file?exists && entry?exists >
								<@link href='${base_url!""}jsp/admin/plugins/forms/FormsDownloadFile.jsp?id_file=${response.file.idFile}&id_response=${response.idResponse}'>
									${response.file.getTitle()!''}
								</@link>
							</#if>
						</#if>
					</p>
				</#list>
			</#if>
		</div>
	</div>
</#macro>
