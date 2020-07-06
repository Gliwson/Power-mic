import {KeycloakConfig, KeycloakInitOptions, KeycloakOptions} from 'keycloak-angular';

const keycloakConfig: KeycloakConfig = {
    url: 'http://localhost:8083/auth',
    realm: 'demo',
    clientId: 'my-app'
};

const keycloakInitOptions: KeycloakInitOptions = {
    onLoad: 'check-sso',
    checkLoginIframe: false
};

const keycloakOptions: KeycloakOptions = {
    config: keycloakConfig,
    initOptions: keycloakInitOptions,
    enableBearerInterceptor: true
};

export const environment = {
    production: true,
    keycloakOptions,
    apiUrl: '/power/api'
};
