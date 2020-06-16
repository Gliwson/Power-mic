import {KeycloakService} from 'keycloak-angular';
import {environment} from '../environments/environment';

export function initializer(keycloak: KeycloakService): () => Promise<any> {
    return (): Promise<any> => {
        return new Promise(async (resolve, reject) => {
            try {
                await keycloak.init({
                    config: {
                        url: environment.keycloak.issuer,
                        realm: environment.keycloak.realm,
                        clientId: environment.keycloak.clientId
                    },
                    initOptions: {
                        onLoad: 'check-sso',
                        checkLoginIframe: false
                    },
                    enableBearerInterceptor: true,
                    bearerPrefix: 'Bearer',
                    bearerExcludedUrls: [
                        '/assets',
                        '/clients/public']
                });
                resolve();
            } catch (error) {
                reject(error);
            }
        });
    };
}
