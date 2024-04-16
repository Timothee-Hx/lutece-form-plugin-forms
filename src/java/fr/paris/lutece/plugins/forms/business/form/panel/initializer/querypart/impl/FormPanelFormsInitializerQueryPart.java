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
package fr.paris.lutece.plugins.forms.business.form.panel.initializer.querypart.impl;

import fr.paris.lutece.plugins.forms.business.form.FormParameters;
import org.apache.lucene.search.MatchAllDocsQuery;

/**
 * Implementation of the FormPanelInitializerQueryPart associate to the FormPanelFormsInitializer
 */
public class FormPanelFormsInitializerQueryPart extends AbstractFormPanelInitializerQueryPart
{
    /**
     * Constructor
     */
    public FormPanelFormsInitializerQueryPart( )
    {
        super( );
        setFormPanelInitializerSelectQuery( new MatchAllDocsQuery( ) );
    }
    public FormPanelFormsInitializerQueryPart( javax.servlet.http.HttpServletRequest request )
    {
        super( );
        fr.paris.lutece.api.user.User user = fr.paris.lutece.portal.service.admin.AdminUserService.getAdminUser( request );
        java.util.List<fr.paris.lutece.plugins.forms.business.Form> listForms = fr.paris.lutece.plugins.forms.business.FormHome.getFormList();
        listForms = (java.util.List<fr.paris.lutece.plugins.forms.business.Form>) fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService.getAuthorizedCollection( listForms, user );
        java.util.List<Integer> listIds = new java.util.ArrayList<Integer>();
        for ( fr.paris.lutece.plugins.forms.business.Form form : listForms )
        {
            listIds.add( form.getId() );
        }
        // sort the list
        java.util.Collections.sort( listIds );
        java.util.List<java.util.List<Integer>> listIdsList = new java.util.ArrayList<java.util.List<Integer>>();
        for (int i = 0; i < listIds.size(); i++)
        {
            // if there is a gap between the current id and the previous one make a new list
            if (i == 0 || listIds.get(i) != listIds.get(i - 1) + 1)
            {
                listIdsList.add(new java.util.ArrayList<Integer>());
            }
            listIdsList.get(listIdsList.size() - 1).add(listIds.get(i));
        }
        java.util.List< org.apache.lucene.search.Query > queries = new java.util.ArrayList<org.apache.lucene.search.Query>();
        for (int i = 0; i < listIdsList.size(); i++)
        {
            if (listIdsList.get(i).size() == 1)
            {
                queries.add(org.apache.lucene.document.IntPoint.newExactQuery("id_form", listIdsList.get(i).get(0)));
            }
            else
            {
                queries.add(org.apache.lucene.document.IntPoint.newRangeQuery("id_form", listIdsList.get(i).get(0), listIdsList.get(i).get(listIdsList.get(i).size() - 1)));
            }
        }
        org.apache.lucene.search.BooleanQuery.Builder builder = new org.apache.lucene.search.BooleanQuery.Builder();
        for (org.apache.lucene.search.Query query : queries)
        {
            builder.add(query, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
        }
        org.apache.lucene.search.Query queryForms = builder.build();

        setFormPanelInitializerSelectQuery( queryForms );

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void buildFormPanelInitializerQuery( FormParameters formParameters )
    {
        // There is nothing to do with the FormParameters for this FormPanelInitializer
    }

}
