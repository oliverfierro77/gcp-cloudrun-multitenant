package com.pudulabs.cloudrun.dataclean.config;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.ProjectName;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * DataClean Secrets Load Service (GCP Secret Manager)
 *
 * @author Oliver Fierro V.
 */
@Slf4j
@Configuration
public class Secret {

    public Map<String, String> secretVar = new HashMap<>();

    public Map<String, Map<String, String>> secretTenantAllCountries = new HashMap<>();

    public Map<String, String> secretTenantCL = new HashMap<>();

    public Map<String, String> secretTenantAR = new HashMap<>();

    public Map<String, String> secretTenantPE = new HashMap<>();

    public Map<String, String> secretTenantCO = new HashMap<>();

    private String projectId;

    private String versionId;

    public Secret(@Value ("${gcp.project}") String projectId, @Value ("${gcp.secrets.versionId}") String versionId) {
        this.projectId = projectId;
        this.versionId = versionId;
        accessSecretVersion();
    }

    public Map getSecretVar() {
        return secretVar;
    }

    public void setSecretVar(Map secretVar) {
        this.secretVar = secretVar;
    }

    public Map<String, Map<String, String>> getSecretTenantAllCountries() {
        return secretTenantAllCountries;
    }

    public void accessSecretVersion() {

        try {
            log.info("DataClean Init accessSecretVersion");
            ProjectName projectName = ProjectName.of(projectId);
            SecretManagerServiceClient client = SecretManagerServiceClient.create();
            SecretManagerServiceClient.ListSecretsPagedResponse pagedResponse = client.listSecrets(projectName);

            // List all secrets.
            pagedResponse
                    .iterateAll()
                    .forEach(
                            secret -> {
                                String secretName = secret.getName().substring(secret.getName().lastIndexOf("/")+1);
                                SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretName, versionId);
                                AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);
                                System.out.println("My secret: " + secretName + "|" + response.getPayload().getData().toStringUtf8());
                                secretVar.put(secretName, response.getPayload().getData().toStringUtf8());
                            });

            //Assign Tenant to Secret Map
            for (Map.Entry<String, String> entry : secretVar.entrySet()) {
                String value = entry.getValue();
                String key = entry.getKey();
                if (key.startsWith("CL")){
                    secretTenantCL.put(key, value);
                } else if (key.startsWith("AR")){
                    secretTenantAR.put(key, value);
                }
                //Add secret from others countries
            }

            secretTenantAllCountries.put("CL", secretTenantCL);
            secretTenantAllCountries.put("AR", secretTenantAR);
            //Add country map to secretTenantAllCountries

        } catch (IOException e) {
            log.error("DataClean: {}", e.getMessage());
        }
    }
}
