/*
 * Copyright (c) 2002-2022, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.forms.export.csv;

import fr.paris.lutece.plugins.forms.business.*;
import fr.paris.lutece.plugins.forms.business.form.FormResponseItem;
import fr.paris.lutece.portal.service.i18n.I18nService;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 
 * Class which contains all the data needed to build the CSV export
 *
 */
public class FormResponseCsvExport
{

    private static final String MESSAGE_EXPORT_FORM_TITLE = "forms.export.formResponse.form.title";
    private static final String MESSAGE_EXPORT_FORM_STATE = "forms.export.formResponse.form.state";
    private static final String MESSAGE_EXPORT_FORM_DATE_CREATION = "forms.export.formResponse.form.date.creation";
    private static final String MESSAGE_EXPORT_FORM_DATE_UPDATE = "forms.export.formResponse.form.date.update";

    private final CSVHeader _csvHeader = new CSVHeader( );
    private String _csvSeparator;

    public FormResponseCsvExport( )
    {
        _csvSeparator = MultiviewConfig.getInstance( ).getCsvSeparator( );
    }
    /*
     * Sort list of FormResponse by question export order
     */
    public List<FormResponse> sortFormResponseListByQuestionExportDisplayOrder(List<FormResponse> formResponseList,  List<Question> listQuestions)
    {
     List <FormResponse> sortedFormResponseList = new ArrayList<>();
        HashMap<Integer, FormResponse> mapFormResponse = new HashMap<>();
        for (int i = 0; i < listQuestions.size(); i++)
        {
            Question question = listQuestions.get(i);
                for (FormResponse formResponse : formResponseList)
                {
                    mapFormResponse.put(question.getExportDisplayOrder(), formResponse);
                }
        }
        for (Integer key : mapFormResponse.keySet())
        {
            sortedFormResponseList.add(mapFormResponse.get(key));
        }
        return sortedFormResponseList;
    }
        /**
         * Build the CSV string for column line
         */
    public String buildCsvColumnToExport( List<FormResponseItem> formResponseItems )
    {
    	List<FormResponse> formResponseList = getFormResponseFromItemList(formResponseItems);
    	int idForm = formResponseList.get(0).getFormId();
        List<Question> listQuestions = QuestionHome.getListQuestionByIdForm(idForm);
        formResponseList = sortFormResponseListByQuestionExportDisplayOrder(formResponseList, listQuestions);
        listQuestions.sort(Comparator.comparingInt(Question::getExportDisplayOrder));

        for ( Question question : listQuestions )
            {
                if ( question.isResponseExportable( ) )
                {
                    _csvHeader.addHeadersWithIterations(question, formResponseList);
                }
            }

        StringBuilder sbCsvColumn = new StringBuilder( );

        sbCsvColumn.append( CSVUtil.safeString( I18nService.getLocalizedString( MESSAGE_EXPORT_FORM_TITLE, I18nService.getDefaultLocale( ) ) ) );
        sbCsvColumn.append( _csvSeparator );
        sbCsvColumn.append( CSVUtil.safeString( I18nService.getLocalizedString( MESSAGE_EXPORT_FORM_DATE_CREATION, I18nService.getDefaultLocale( ) ) ) );
        sbCsvColumn.append( _csvSeparator );
        sbCsvColumn.append( CSVUtil.safeString( I18nService.getLocalizedString( MESSAGE_EXPORT_FORM_DATE_UPDATE, I18nService.getDefaultLocale( ) ) ) );
        sbCsvColumn.append( _csvSeparator );
        sbCsvColumn.append( CSVUtil.safeString( I18nService.getLocalizedString( MESSAGE_EXPORT_FORM_STATE, I18nService.getDefaultLocale( ) ) ) );
        sbCsvColumn.append( _csvSeparator );

        for ( Question question : _csvHeader.getQuestionColumns( ) )
        {
        	boolean bIsIteration = _csvHeader.getIterationsQuestionColumns().contains(question);
            sbCsvColumn.append( CSVUtil.safeString( CSVUtil.buildColumnName( question, bIsIteration ) ) ).append( _csvSeparator );
        }

        return sbCsvColumn.toString( );
    }
    
    private List<FormResponse> getFormResponseFromItemList(List<FormResponseItem> formResponseItems)
    {
    	List<FormResponse> formResponseList = new ArrayList<>();
    	for (FormResponseItem formResponseItem : formResponseItems)
    	{
    		 FormResponse formResponse = FormResponseHome.findByPrimaryKeyForIndex( formResponseItem.getIdFormResponse() );
    		 if (formResponse != null)
    		 {
    			 formResponseList.add(formResponse);
    		 }
    	}
    	return formResponseList;
    }

    /**
     * Build the CSV string for all data lines
     */
    public String buildCsvDataToExport( FormResponse formResponse, String state )
    {
        CSVDataLine csvDataLine = new CSVDataLine( formResponse, state, _csvSeparator );

        for ( FormResponseStep formResponseStep : formResponse.getSteps( ) )
        {
            for ( FormQuestionResponse formQuestionResponse : formResponseStep.getQuestions( ) )
            {
                if ( formQuestionResponse.getQuestion( ).isResponseExportable( ) )
                {
                    csvDataLine.addData( formQuestionResponse );
                }
            }
        }

        StringBuilder sbCsvData = new StringBuilder( );

        StringBuilder sbRecordContent = new StringBuilder( );
        sbRecordContent.append( csvDataLine.getCommonDataToExport( ) );

        for ( Question question : _csvHeader.getQuestionColumns( ) )
        {
            sbRecordContent.append( CSVUtil.safeString( Objects.toString( csvDataLine.getDataToExport( question ), StringUtils.EMPTY ) ) )
                    .append( _csvSeparator );
        }

        sbCsvData.append( sbRecordContent.toString( ) );

        return sbCsvData.toString( );
    }
}
