import {KeycloakConfig, KeycloakInitOptions, KeycloakOptions} from 'keycloak-angular';

const keycloakConfig: KeycloakConfig = {
    url: 'http://localhost:8080/auth',
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
    enableBearerInterceptor: true,
    bearerPrefix: 'Bearer',
    bearerExcludedUrls: [
        'main.js',
    ],
};

export const environment = {
    production: false,
    keycloakOptions,
    apiUrl: '/power/api'
};
