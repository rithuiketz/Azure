package com.azure.vault.secrets;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Map;
import java.util.Optional;

/**
 * Unit test for simple App.
 */
public class AzureSecretsTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testAzureSecret()
    {
        String vaulturl ="https://am01-dst-dev-akv02.vault.azure.net/";
        String tenantId = "d1e23d19-ded6-4d66-850c-0d4f35bf2edc";
        String principalId = "0725ceb8-7d5a-45fc-9048-b49e792df0b6";
        String appSec = "Li-8Q~wCqaUGBB36jFOddUE01vy~hi9wVNRhca4g";
        Map<String,String> props = System.getenv();
        props.put("AZURE_CLIENT_ID", principalId);
                props.put("AZURE_CLIENT_SECRET", appSec);
                props.put("AZURE_TENANT_ID",tenantId);
        AzureSecretService service = new AzureSecretService(vaulturl,principalId,tenantId);
        Optional<String> secret = service.getSecret("KnAdlsSaas");
        assert secret.isPresent();

    }
}
