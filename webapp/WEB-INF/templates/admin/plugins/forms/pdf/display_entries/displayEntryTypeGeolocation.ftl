<#--
Macro: displayEntryGeolocation
Description: Display an entry of type geolocation
Parameters: entry, list_responses
-->
<#macro displayEntryTypeGeolocation entry, list_responses >
		<#assign responseX = "" >
		<#assign responseY = "" >
		<#if getResponseContainingTheFieldWithCode( list_responses, "address" )?? >
			<#assign responseAddress = getResponseContainingTheFieldWithCode( list_responses, "address" ).toStringValueResponse >
		</#if>
		<#if getResponseContainingTheFieldWithCode( list_responses, "geometry" )?? >
			<#assign responseGeometry = getResponseContainingTheFieldWithCode( list_responses, "geometry" ).toStringValueResponse >
		</#if>
		<#if getResponseContainingTheFieldWithCode( list_responses, "X" )?? >
			<#assign responseX = getResponseContainingTheFieldWithCode( list_responses, "X" ).toStringValueResponse >
		</#if>
		<#if getResponseContainingTheFieldWithCode( list_responses, "Y" )?? >
			<#assign responseY = getResponseContainingTheFieldWithCode( list_responses, "Y" ).toStringValueResponse >
		</#if>
		<#if responseX == "" || responseX == "0" || responseY == "" || responseY == "0">
			#i18n{forms.entryType.geolocalisation.message.noGeolocation}
		<#else>
			${(responseAddress)!} <br>
			type : ${(responseGeometry)!} <br>
			longitude : ${responseX} <br>
			latitude : ${responseY}
		</#if>
</#macro>
