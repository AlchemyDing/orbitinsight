import { defineConfig } from "umi";
import routes from "./routes";
const { REACT_APP_ENV } = process.env;
import { baseUrl } from "./baseUrl";

export default defineConfig({
  routes,
  npmClient: 'pnpm',
  define: { 
    "process.env":{
      APP_API_URL_ZH:baseUrl,
      NODE_ENV: REACT_APP_ENV
    }
  }
});
