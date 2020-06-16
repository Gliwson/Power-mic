export const environment = {
  apiUrl: 'http://localhost:8080',
  production: true,
  envName: 'local',
  keycloak: {

    issuer: 'http://localhost:8083/auth/',

    realm: 'gliwson',

    clientId: 'tasks-ui',
  }
};
