<#--
Macro: displayEntry
Description: Call the macro corresponding to the entry type
Parameters: q (forms.FormQuestionResponse) - The form question response
-->
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeText.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeArray.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeAutoFileReading.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeCamera.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeCartography.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeCheckBox.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeComment.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeDate.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeFile.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeGalleryImage.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeGeolocation.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeImage.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeMyLuteceUser.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeNumber.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeNumbering.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeRadioButton.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeSelect.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeSelectOrder.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeTelephone.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeTermsOfService.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeText.ftl" />
<#include "/admin/plugins/workflow/modules/formspdf/display_entries/displayEntryTypeTextArea.ftl" />


<#macro displayEntry q>
    <#if q??>
        <#assign entry=q.question.entry >
        <#assign list_responses=q.getEntryResponse() >

        <#if entry.entryType.beanName == "forms.entryTypeArray">
            <@displayEntryTypeArray entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeAutomaticFileReading">
            <@displayEntryTypeAutoFileReading entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeCamera">
            <@displayEntryTypeCamera entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeCartography">
            <@displayEntryTypeCartography entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeCheckbox">
            <@displayEntryTypeCheckbox entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeComment">
            <@displayEntryTypeComment entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeDate">
            <@displayEntryTypeDate entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeFile">
            <@displayEntryTypeFile entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeGalleryImage">
            <@displayEntryTypeGalleryImage entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeGeolocation">
            <@displayEntryTypeGeolocation entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeImage">
            <@displayEntryTypeImage entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeMyLuteceUser">
            <@displayEntryTypeMyLuteceUser entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeNumber">
            <@displayEntryTypeNumber entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeNumbering">
            <@displayEntryTypeNumbering entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeRadioButton">
            <@displayEntryTypeRadioButton entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeSelect">
            <@displayEntryTypeSelect entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeSelectOrder">
            <@displayEntryTypeSelectOrder entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeTelephoneNumber">
            <@displayEntryTypeTelephone entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeTermsOfService">
            <@displayEntryTypeTermsOfService entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeText">
            <@displayEntryTypeText entry=entry list_responses=list_responses  />
        <#elseif entry.entryType.beanName == "forms.entryTypeTextArea">
            <@displayEntryTypeTextArea entry=entry list_responses=list_responses  />
        <#else>
            <@displayEntryTypeText entry=entry list_responses=list_responses  />
        </#if>
    </#if>
</#macro>
