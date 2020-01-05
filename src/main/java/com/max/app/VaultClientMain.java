package com.max.app;


import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class VaultClientMain {

    private static final Logger LOG = LoggerFactory.getLogger(VaultClientMain.class);

    public static void main(String[] args) throws Exception {

        final String vaultPath = "secret/service/customer-service/database";

        LOG.info("url: {}", readFieldFromVault(vaultPath, "url"));
        LOG.info("value: {}", readFieldFromVault(vaultPath, "username"));
        LOG.info("password: {}", readFieldFromVault(vaultPath, "password"));

        LOG.info("VaultClientMain done. java version {}", System.getProperty("java.version"));
    }


    private static String readFieldFromVault(String path, String field) {
        try {
            final VaultConfig vaultConfig = new VaultConfig()
                    .address(System.getenv("VAULT_ADDR"))
                    .engineVersion(2)
                    .token(getTokenForRole())
                    .build();

            final Vault vault = new Vault(vaultConfig);

            Map<String, String> temp = vault.logical()
                    .read(path)
                    .getData();

            return vault.logical()
                    .read(path)
                    .getData().get(field);
        }
        catch (VaultException vaultEx) {
            throw new ExceptionInInitializerError(vaultEx);
        }
    }

    private static String getTokenForRole() throws VaultException {
        VaultConfig vaultConfig = new VaultConfig()
                .engineVersion(2)
                .address(System.getenv("VAULT_ADDR"))
                .build();
        final Vault vault = new Vault(vaultConfig);

        final String roleId = "7016475c-2435-fe8f-245a-87292798be6c";
        final String secretId = "100f3689-72be-e888-92f6-2d959a083510";

        AuthResponse response = vault.auth().loginByAppRole(roleId, secretId);

        final String token = response.getAuthClientToken();

//        LOG.info("token: {}", response.getAuthPolicies());

        return token;
    }
}
