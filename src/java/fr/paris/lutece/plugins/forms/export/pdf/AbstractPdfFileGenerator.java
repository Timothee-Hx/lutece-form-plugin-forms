package fr.paris.lutece.plugins.forms.export.pdf;

import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.MultiviewConfig;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.form.FormItemSortConfig;
import fr.paris.lutece.plugins.forms.business.form.column.IFormColumn;
import fr.paris.lutece.plugins.forms.business.form.filter.FormFilter;
import fr.paris.lutece.plugins.forms.business.form.panel.FormPanel;
import fr.paris.lutece.plugins.forms.export.AbstractFileGenerator;
import fr.paris.lutece.plugins.forms.service.provider.GenericFormsProvider;
import fr.paris.lutece.plugins.genericattributes.business.Response;
import fr.paris.lutece.plugins.html2pdf.service.PdfConverterService;
import fr.paris.lutece.plugins.html2pdf.service.PdfConverterServiceException;
import fr.paris.lutece.plugins.workflowcore.service.provider.InfoMarker;
import fr.paris.lutece.portal.business.file.File;
import fr.paris.lutece.portal.business.file.FileHome;
import fr.paris.lutece.portal.business.physicalfile.PhysicalFile;
import fr.paris.lutece.portal.business.physicalfile.PhysicalFileHome;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.html.HtmlTemplate;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;

public abstract class AbstractPdfFileGenerator extends AbstractFileGenerator {
	
	protected static final String CONSTANT_MIME_TYPE_PDF = "application/pdf";
	protected static final String EXTENSION_PDF = ".pdf";
	protected static final String LOG_ERROR_PDF_EXPORT_GENERATION = "Error during PDF export generation";
	private static final String KEY_LABEL_YES = "portal.util.labelYes";
    private static final String KEY_LABEL_NO = "portal.util.labelNo";

    private static final String LINK_MESSAGE_FO = "forms.export.pdf.label.link_FO";
    private static final String LINK_MESSAGE_BO = "forms.export.pdf.label.link_BO";
    private static final String PUBLISHED = "forms.export.pdf.published";
    private static final String NOT_PUBLISHED = "forms.export.pdf.unpublished";
    private static final String RESPONSE_CREATED = "forms.export.formResponse.form.date.creation";
    private static final String RESPONSE_UPDATED = "forms.export.formResponse.form.date.update";
    private static final String MESSAGE_EXPORT_FORM_TITLE = "forms.export.formResponse.form.title";
    private static final String MESSAGE_EXPORT_FORM_STATE = "forms.export.formResponse.form.state";

    private static final String RESPONSE_NUMBER = "response_number";
    
    protected final String _formTitle;
    protected HttpServletRequest _request;
    protected String _fileDescription;
    protected String _fileName;
    protected FormPanel _formPanel;
    protected List<IFormColumn> _listFormColumn;
    protected List<FormFilter> _listFormFilter;
    protected FormItemSortConfig _sortConfig;
    protected Form _form;

    protected AbstractPdfFileGenerator(String fileName, String formTitle, FormPanel formPanel, List<IFormColumn> listFormColumn,
			List<FormFilter> listFormFilter, FormItemSortConfig sortConfig, String fileDescription, HttpServletRequest request, Form form) {
		super(fileName, formPanel, listFormColumn, listFormFilter, sortConfig, fileDescription, request, form);
		_formTitle = formTitle;
        _request = request;
        _fileDescription = fileDescription;
        _fileName = fileName;
        _formPanel = formPanel;
        _listFormColumn = listFormColumn;
        _listFormFilter = listFormFilter;
        _sortConfig = sortConfig;
        _form = form;
	}
    protected String getTemplateExportPDF()
    {
        File templateFile = FileHome.findByPrimaryKey(MultiviewConfig.getInstance().getIdFileTemplatePdf());
        if (templateFile != null && templateFile.getPhysicalFile() != null)
        {
            PhysicalFile physicalTemplateFile = PhysicalFileHome.findByPrimaryKey(templateFile.getPhysicalFile().getIdPhysicalFile());
            if (physicalTemplateFile != null)
            {
                return new String(physicalTemplateFile.getValue());
            } else {
                AppLogService.error(LOG_ERROR_PDF_EXPORT_GENERATION + " : " + "Physical file not found for file " + templateFile.getTitle());
            }
        }
        return null;
    }
/*
    public String generateTemplateForPdfExportResponses (Form form,  List<Question> listQuestions )
    {
        String space = "                ";
        String freemarkerOpenBracket = "${";
        String freemarkerCloseBracket = "}";
        Document document = new Document("");

        String InfoMessageResponseCreated = I18nService.getLocalizedString( RESPONSE_CREATED, I18nService.getDefaultLocale( ));
        String InfoMessageResponseUpdated = I18nService.getLocalizedString( RESPONSE_UPDATED, I18nService.getDefaultLocale( ));
        String InfoMessageResponseState = I18nService.getLocalizedString( MESSAGE_EXPORT_FORM_STATE, I18nService.getDefaultLocale( ));
        String InfoMessageFormTitle = I18nService.getLocalizedString( MESSAGE_EXPORT_FORM_TITLE, I18nService.getDefaultLocale( ));
        Element div = document.appendElement("div");
        div.addClass("response_container");

        div.appendElement("h1").text(InfoMessageFormTitle+ " : " + form.getTitle()   + space + "# " + "$$" + RESPONSE_NUMBER + "$$");
        div.appendElement("h3").text(InfoMessageResponseCreated+ " : " + freemarkerOpenBracket + "creation_date!" + freemarkerCloseBracket);
        div.appendElement("h3").text(InfoMessageResponseUpdated+ " : " + freemarkerOpenBracket + "update_date!" + freemarkerCloseBracket);
        div.appendElement("h3").text(InfoMessageResponseState + " : " + freemarkerOpenBracket + "status!" + freemarkerCloseBracket);
        Element table = div.appendElement("table");
        Element tbody = table.appendElement("tbody");

        int numRows = listQuestions.size();

        for (int i = 0; i < numRows; i++)
        {
            Element row = tbody.appendElement("tr");
            Element cellTitle = row.appendElement("td");
            cellTitle.text(listQuestions.get(i).getTitle() + " : ");
            Element cellResponse = row.appendElement("td");
            cellResponse.text(freemarkerOpenBracket+"position_"+listQuestions.get(i).getEntry().getIdEntry()+"!"+freemarkerCloseBracket);
        }
        return document.outerHtml();
    }


*/



    public  HashMap<Integer, FormQuestionResponse>  formResponseListToHashmap( List<FormQuestionResponse>  formQuestionResponseList)
    {
        HashMap<Integer, FormQuestionResponse> formResponseListByEntryId = new HashMap<>();
        for (int i = 0; i < formQuestionResponseList.size(); i++)
        {
            int idEntry = formQuestionResponseList.get(i).getQuestion().getIdEntry();
            if(formResponseListByEntryId.containsKey(idEntry))
            {
                // This is to had the response to the hashmap when there are iterations in the form (one than one time the same entry)
                List <Response> presentResponses = formResponseListByEntryId.get(formQuestionResponseList.get(i).getQuestion().getIdEntry()).getEntryResponse();
                List <Response> newResponses = formQuestionResponseList.get(i).getEntryResponse();
                presentResponses.addAll(newResponses);
                formQuestionResponseList.get(i).setEntryResponse(presentResponses);
                formResponseListByEntryId.put(idEntry, formQuestionResponseList.get(i));
            }
            else
            {
                formResponseListByEntryId.put(idEntry, formQuestionResponseList.get(i));
            }
        }
        return formResponseListByEntryId;
    }
    /**
     * Fill template with form question response.
     *
     * @param template
     *            the template
     * @param formQuestionResponseList
     *            the form question response list
     * @param listQuestions
     *            the list questions
     * @return the html template
     */
    protected HtmlTemplate fillTemplateWithFormQuestionResponse (String template, List<FormQuestionResponse> formQuestionResponseList, List<Question> listQuestions, FormResponse formResponse)
    {
        Map<String, Object> model = new HashMap<>( );
        Map<String, InfoMarker> collectionMarkersValue = GenericFormsProvider.provideMarkerValues( formResponse, _request );
        markersToModels(model, collectionMarkersValue );
        return AppTemplateService.getTemplateFromStringFtl(template, Locale.getDefault(), model);
    }

    /**
     * In a loop, call the markersToModel method to add the markers to the model
     * @param model
     * @param collectionMarkersValue
     */
    private void markersToModels( Map<String, Object> model, Map<String, InfoMarker> collectionMarkersValue  )
    {
        for ( int i = 0; i < collectionMarkersValue.size(); i++ )
        {
            String key = collectionMarkersValue.keySet().toArray()[i].toString();
            markerToModel( model, collectionMarkersValue, key );



        }
    }

    /**
     * Add the markers to the model
     * @param model
     * @param collectionMarkersValue
     * @param key
     */
    private void markerToModel( Map<String, Object> model, Map<String, InfoMarker> collectionMarkersValue, String key  )
    {
        if(key.contains( "position_" ) )
        {
            FormQuestionResponse formQuestionResponse = (FormQuestionResponse) collectionMarkersValue.get( key ).getData( );
            if(formQuestionResponse.getQuestion().getEntry() != null)
            {
                model.put( key, formQuestionResponse );
                //admin/plugins/forms/pdf/display_entries/display_entries/displayEntry.ftl
             //   String displayEntryHtml = AppTemplateService.getTemplateFromStringFtl()
                System.out.println("key : " + key + " value : " + formQuestionResponse);
            }
        }
        else
        {
            model.put( key, collectionMarkersValue.get( key ).getData( ) );
            System.out.println("key : " + key + " value : " + collectionMarkersValue.get( key ).getData( ));
        }
    }


    protected void generatePdfFile(Path directoryFile, String strFilledTemplate, String fileName, boolean isCustomTemplate ) throws PdfConverterServiceException, IOException
    {
        try ( OutputStream outputStream = Files.newOutputStream( directoryFile.resolve( fileName + EXTENSION_PDF ) ) )
        {

            Document doc = Jsoup.parse( strFilledTemplate, UTF_8 );
            doc.outputSettings( ).syntax( Document.OutputSettings.Syntax.xml );
            doc.outputSettings( ).escapeMode( EscapeMode.base.xhtml );
            doc.outputSettings( ).charset( UTF_8 );

            PdfConverterService.getInstance( ).getPdfBuilder( ).reset( ).withHtmlContent( doc.html( ) ).notEditable( ).render( outputStream );
        }
        catch(PdfConverterServiceException | IOException e)
        {
            AppLogService.error( "Error generating pdf for file " + fileName, e );
            throw e;
        }
    }
    protected void generatedPdfForRangeOfFormResponses ( Path directoryFile, String strTemplateResponses, List<Question> listQuestions, List<FormResponse> listResponse, List<List<FormQuestionResponse>> formQuestionResponseList, int startRange, String fileName, boolean isCustomTemplate) throws IOException
    {
        try {
            String strFilledTemplate = "";
            for (int i = 0; i < formQuestionResponseList.size(); i++) {
                List<FormQuestionResponse> formQuestionResponseList1 = formQuestionResponseList.get(i);
                HtmlTemplate filledTemplate = fillTemplateWithFormQuestionResponse(strTemplateResponses, formQuestionResponseList1, listQuestions, listResponse.get(i));
                String strSingleTemplate = "";

                strFilledTemplate += strSingleTemplate;
            }
            generatePdfFile(directoryFile, strFilledTemplate, fileName, isCustomTemplate);
        } catch (IOException e) {
            AppLogService.error( LOG_ERROR_PDF_EXPORT_GENERATION, e );
            throw e;
        } catch (Exception e) {
            AppLogService.error( LOG_ERROR_PDF_EXPORT_GENERATION, e );
        }

    }

}
