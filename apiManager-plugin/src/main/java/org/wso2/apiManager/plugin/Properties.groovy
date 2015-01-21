package org.wso2.apiManager.plugin

import com.eviware.soapui.SoapUI

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

class Properties {
    public static void setSystemProperties(){
        String keyStoreFileName = SoapUI.getSettings().getString("SSLSettings@keyStore", null);
        String keyStorePassword = SoapUI.getSettings().getString("SSLSettings@keyStorePassword", null);

        System.setProperty("javax.net.ssl.trustStore",keyStoreFileName);
        System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

    }
}
