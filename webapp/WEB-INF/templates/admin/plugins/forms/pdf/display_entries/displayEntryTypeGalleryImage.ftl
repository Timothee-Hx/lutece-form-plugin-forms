<#--
Macro: displayEntryTypeGalleryImage
Description: Display an entry of type gallery image
Parameters: entry, list_responses
-->
<#macro displayEntryTypeGalleryImage entry, list_responses >
	<div style="display: flex; flex-wrap: wrap;">
		<div style="flex: 0 0 75%; max-width: 75%;">
			<#if list_responses?has_content>
				<#list list_responses as response>
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
				</#list>
			</#if>
		</div>
	</div>
</#macro>
