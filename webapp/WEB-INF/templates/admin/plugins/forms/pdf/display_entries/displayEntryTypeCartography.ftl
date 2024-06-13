<#--
Macro: displayEntryTypeCartography
Description: Display an entry of type Cartography
Parameters: entry, list_responses
Entry not found in basic config of forms entrytype, not tested
-->
<#macro displayEntryTypeCartography entry, list_responses >
	<div style="display: flex; flex-wrap: wrap;">
		<div style="flex: 0 0 25%; max-width: 25%;"><p style="text-align: right; font-weight: bold;">${entry.title!''}</p></div>
		<div style="flex: 0 0 75%; max-width: 75%;">
			<p>
				<#assign coordinate = "" >
				<#assign datalayer = "" >
				<#if getResponseContainingTheFieldWithCode( list_responses, "coordinates_geojson" )?? >
					<#assign coordinate = getResponseContainingTheFieldWithCode( list_responses, "coordinates_geojson" ).toStringValueResponse >
				</#if>
				<#if getResponseContainingTheFieldWithCode( list_responses, "DataLayer" )?? >
					<#assign datalayer = getResponseContainingTheFieldWithCode( list_responses, "DataLayer" ).toStringValueResponse >
				</#if>
			</p>
		</div>
	</div>
</#macro>
