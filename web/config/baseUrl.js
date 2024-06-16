const { REACT_APP_ENV } = process.env;

export const baseUrl = {
  dev: "https://gpt-data-serve.wangxuelong.vip",
  test: "https://gpt-data-serve.wangxuelong.vip2",
  prod: "https://gpt-data-serve.wangxuelong.vip",
}[REACT_APP_ENV];
