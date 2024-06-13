<#--
Macro: displayEntryTypeArray
Description: Display an entry of type array
Parameters: entry, list_responses
Entry not found in basic config of forms entrytype, not tested
-->
<#macro displayEntryTypeArray entry, list_responses >
<#--Can't save an answer -->
	<div style="display: flex; flex-wrap: wrap;">
		<div style="flex: 0 0 25%; max-width: 25%;"><p style="text-align: right; font-weight: bold;">${entry.title!''}</p></div>
		<div style="flex: 0 0 75%; max-width: 75%;">
			<@table>
				<#assign x=getFieldValueByCode(entry, "array_row")?number>
				<#list 1..x+1 as i>
					<@tr>
						<#assign y=getFieldValueByCode(entry, "array_column")?number>
						<#list 1..y+1 as j>
							<@td>
								<#assign title="">
								<#assign responseValue = "">
								<#list entry.fields as field>
									<#assign value=i+"_"+j>
									<#if field.value == value>
										<#assign title=field.title!>
										<#if list_responses??>
											<#list list_responses as response>
												<#if response.field?has_content && response.field.value == value && response.toStringValueResponse?has_content>
													<#assign responseValue = response.toStringValueResponse>
												</#if>
											</#list>
										</#if>
									</#if>
								</#list>
								<#if j==1 && i!=1>
									<b>${title!}</b>
								<#elseif i == 1 && j != 1>
									<b>${title!}</b>
								<#else>
									${responseValue!''}
								</#if>
							</@td>
						</#list>
					</@tr>
				</#list>
			</@table>
		</div>
	</div>
</#macro>
