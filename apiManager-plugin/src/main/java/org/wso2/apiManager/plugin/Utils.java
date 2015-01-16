/*
*  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.apiManager.plugin;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.rest.RestService;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.support.StringUtils;
import com.eviware.x.form.XFormDialog;
import com.eviware.x.form.support.ADialogBuilder;
import com.smartbear.swagger.SwaggerImporter;
import com.smartbear.swagger.SwaggerUtils;
import org.wso2.apiManager.plugin.dataObjects.APIInfo;
import org.wso2.apiManager.plugin.ui.APIModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    /*
    * This method checks whether the given URL is a correct one.
    * */
    public static URL validateURL(String urlString) {
        if (StringUtils.isNullOrEmpty(urlString)) {
            return null;
        }

        if (!urlString.toLowerCase().startsWith("http://") && !urlString.toLowerCase().startsWith("https://")) {
            return null;
        }

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            SoapUI.logError(e);
            return null;
        }
    }

    public static List<APIInfo> showSelectAPIDefDialog(final List<APIInfo> apis) {
        final XFormDialog dialog = ADialogBuilder.buildDialog(APIModel.class);
        final Object[][] tableData = convertToTableData(apis);
        final String[] columnNames = {"Name", "Version", "Provider", "Description"};

        TableModel tableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return tableData.length;
            }

            @Override
            public int getColumnCount() {
                // We have a hardcoded set of columns
                return 4;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return tableData[rowIndex][columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };


        JTable apiTable = new JTable(tableData, columnNames);
        apiTable.setCellSelectionEnabled(false);
        apiTable.setColumnSelectionAllowed(false);
        apiTable.setRowSelectionAllowed(true);
        apiTable.setPreferredScrollableViewportSize(new Dimension(500,200));

        JScrollPane scrollPane = new JScrollPane(apiTable);
        scrollPane.setPreferredSize(new Dimension(500,200));

        dialog.setFormFieldProperty("component", scrollPane);
        dialog.setFormFieldProperty("preferredSize", new Dimension(500,200));
//        dialog.setFormFieldProperty("preferredSize", new Dimension(500, 250));


        /*ListModel<String> listBoxModel = new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return apis.size();
            }

            @Override
            public String getElementAt(int index) {
                return apis.get(index).getName();
            }
        };*/
        /*final JList apiListBox = new JList(listBoxModel);
        dialog.getFormField(APIModel.NAME).setProperty("component", new JScrollPane(apiListBox));
        dialog.getFormField(APIModel.NAME).setProperty("preferredSize", new Dimension(500, 150));
        dialog.setValue(APIModel.VERSION, null);
        dialog.setValue(APIModel.PROVIDER, null);
        dialog.getFormField(APIModel.DESCRIPTION).setProperty("preferredSize", new Dimension(500, 150));
        dialog.setValue(APIModel.DESCRIPTION, null);

        apiListBox.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int[] selected = apiListBox.getSelectedIndices();
                if (selected != null && selected.length == 1) {
                    int selectedNo = selected[0];
                    dialog.getFormField(APIModel.VERSION).setValue(apis.get(selectedNo).getVersion());
                    dialog.getFormField(APIModel.PROVIDER).setValue(apis.get(selectedNo).getProvider());
                    dialog.getFormField(APIModel.DESCRIPTION).setValue(apis.get(selectedNo).getDescription());
                } else {
                    dialog.getFormField(APIModel.VERSION).setValue(null);
                    dialog.getFormField(APIModel.PROVIDER).setValue(null);
                    dialog.getFormField(APIModel.DESCRIPTION).setValue(null);
                }
            }
        });
        apiListBox.setSelectedIndex(-1);

        dialog.getFormField(APIModel.NAME).addFormFieldValidator(new XFormFieldValidator() {
            @Override
            public ValidationMessage[] validateField(XFormField formField) {
                int[] selected = apiListBox.getSelectedIndices();
                if (selected == null || selected.length == 0) {
                    return new ValidationMessage[]{new ValidationMessage("Please select at least one API " +
                                                                         "specification to add.", formField)};
                } else {
                    return new ValidationMessage[0];
                }
            }
        });
*/
        if (dialog.show()) {
            int[] selected = apiTable.getSelectedRows();
            ArrayList<APIInfo> selectedAPIs = new ArrayList<APIInfo>();
            for (int no : selected) {
                selectedAPIs.add(apis.get(no));
            }
            return selectedAPIs;
        } else {
            return null;
        }

    }

    public static RestService[] importAPItoProject(APIInfo apiLink, WsdlProject project) {
        SwaggerImporter importer = SwaggerUtils.createSwaggerImporter(apiLink.getSwaggerDocLink(), project);
        SoapUI.log("Importing Swagger from [" + apiLink.getName() + "]");
        return importer.importSwagger(apiLink.getSwaggerDocLink());
    }

    private static Object[][] convertToTableData(List<APIInfo> apiList) {
        Object[][] convertedData = new Object[apiList.size()][4];

        for (int i = 0; i < apiList.size(); i++) {
            APIInfo apiInfo = apiList.get(i);

            convertedData[i][0] = apiInfo.getName();
            convertedData[i][1] = apiInfo.getVersion();
            convertedData[i][2] = apiInfo.getProvider();
            convertedData[i][3] = apiInfo.getDescription();
        }
        return convertedData;
    }
}