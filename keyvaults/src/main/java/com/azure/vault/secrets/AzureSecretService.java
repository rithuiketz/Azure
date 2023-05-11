package com.azure.vault.secrets;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.nimbusds.oauth2.sdk.auth.Secret;

import java.util.Optional;

public class AzureSecretService
{
    private String keyVaultUrl;

    private String principalId;

    private SecretClient secretClient;

    private  String tenantId;

    public AzureSecretService(String keyVaultUrl,String principalId,String tenantId)
    {
        this.keyVaultUrl=keyVaultUrl;
        this.principalId = principalId;
        this.tenantId = tenantId;
    }

    private SecretClient getSecretClient()
    {
        if(this.secretClient == null)
        {
            secretClient = new SecretClientBuilder().
                    vaultUrl(this.keyVaultUrl).
                    credential(new DefaultAzureCredentialBuilder().
                            tenantId(this.tenantId)
                            .managedIdentityClientId(this.principalId).

                            build())
                    .buildClient();
        }
        return secretClient;
    }

    public Optional<String> getSecret(String secName)
    {
        try{
            KeyVaultSecret secret = getSecretClient().getSecret(secName);
            if(secret !=null)
            {
                return Optional.of(secret.getValue());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  Optional.empty();
    }
}
