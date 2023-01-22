import hasPermission from '@/directive/permission/hasPermission'
import hasRole from '@/directive/permission/hasRole';

const install = function (Vue) {
    Vue.directive('hasPermission', hasPermission);
    Vue.directive('hasRole', hasRole);
}

if (window.Vue) {
    window['hasPermission'] = hasPermission;
    window['hasRole'] = hasRole;
    Vue.use(install);
}

export default install;
