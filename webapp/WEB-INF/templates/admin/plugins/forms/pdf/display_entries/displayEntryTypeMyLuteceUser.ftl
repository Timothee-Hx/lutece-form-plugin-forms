<#--
Macro: displayEntryTypeMyLuteceUser
Description: Display an entry of type MyLuteceUser, the plugin MyLuteceUser must be installed
Parameters: entry, list_responses
-->
<#macro displayEntryTypeMyLuteceUser entry, list_responses >
	<#if mylutece_user_login?? && mylutece_user_infos_list?has_content>
		<div style="display: flex; flex-wrap: wrap;">
			<div style="flex: 0 0 25%; max-width: 25%;"><p style="text-align: right; font-weight: bold;">#i18n{forms.entryType.myLuteceUser.labelLogin}</p></div>
			<div style="flex: 0 0 75%; max-width: 75%;">
				<#list mylutece_user_infos_list as user_info>
					<div style="display: flex; flex-wrap: wrap;">
						<div style="flex: 0 0 25%; max-width: 25%;"><p style="text-align: right; font-weight: bold;">#i18n{portal.security.${user_info.code}}</p></div>
						<div style="flex: 0 0 75%; max-width: 75%;">
							<p>${user_info.name}</p>
						</div>
					</div>
				</#list>
			</div>
		</div>
	<#else>
		<div style="display: flex; flex-wrap: wrap;">
			<div style="flex: 0 0 25%; max-width: 25%;"><p style="text-align: right; font-weight: bold;">${entry.title!''}</p></div>
			<div style="flex: 0 0 75%; max-width: 75%;">
				<#if list_responses?has_content>
					<#list list_responses as response>
						<p>
							<#if form_response?? && response.entry??>
								<@link href='${base_url!""}jsp/admin/plugins/forms/DoVisualisationMyLuteceUser.jsp?id_form_response=${form_response.id}&amp;id_entry=${response.entry.idEntry}'>
									${response.toStringValueResponse!''}
								</@link>
							<#else>
								${response.toStringValueResponse!''}
							</#if>
						</p>
					</#list>
				</#if>
			</div>
		</div>
	</#if>
</#macro>
