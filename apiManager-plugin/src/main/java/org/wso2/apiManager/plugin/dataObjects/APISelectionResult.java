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

package org.wso2.apiManager.plugin.dataObjects;

import java.util.List;

public class APISelectionResult {
    private List<APIInfo> apiInfoList;
    private boolean isTestSuiteSelected;
    private boolean isLoadTestSelected;

    public List<APIInfo> getApiInfoList() {
        return apiInfoList;
    }

    public void setApiInfoList(List<APIInfo> apiInfoList) {
        this.apiInfoList = apiInfoList;
    }

    public boolean isTestSuiteSelected() {
        return isTestSuiteSelected;
    }

    public void setTestSuiteSelected(boolean isTestSuiteSelected) {
        this.isTestSuiteSelected = isTestSuiteSelected;
    }

    public boolean isLoadTestSelected() {
        return isLoadTestSelected;
    }

    public void setLoadTestSelected(boolean isLoadTestSelected) {
        this.isLoadTestSelected = isLoadTestSelected;
    }
}