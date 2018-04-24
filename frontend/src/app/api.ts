export class Api {
  private baseUrl: string = 'http://localhost:9090/api';

  getHosts(): string {
    return `${this.baseUrl}/hosts`;
  }
}

export const api = new Api();
