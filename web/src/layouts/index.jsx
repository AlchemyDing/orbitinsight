import { ProLayout } from '@ant-design/pro-layout';
import { Link, Outlet, useAppData, useLocation } from 'umi';
import { ConfigProvider } from 'antd';
import themeConfig from '../../config/themeConfig';

export default function Layout() {
  const { clientRoutes } = useAppData();
  const location = useLocation();
  return (
    <ConfigProvider theme={themeConfig}>
      <ProLayout
        route={clientRoutes[0]}
        location={location}
        title="Umi x Ant Design"
        menuItemRender={(menuItemProps, defaultDom) => {
          if (menuItemProps.isUrl || menuItemProps.children) {
            return defaultDom;
          }
          if (menuItemProps.path && location.pathname !== menuItemProps.path) {
            return (
              <Link to={menuItemProps.path} target={menuItemProps.target}>
                {defaultDom}
              </Link>
            );
          }
          return defaultDom;
        }}
      >
        <Outlet />
      </ProLayout>
    </ConfigProvider>
  );
}