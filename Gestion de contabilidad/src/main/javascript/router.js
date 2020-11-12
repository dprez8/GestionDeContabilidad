import VueRouter from 'vue-router'
import axios from 'axios'

import panel from './pages/panel'
import login from './pages/login'
import error404 from './pages/error404'
import error500 from './pages/error500'
import inicio from './pages/inicio'
import operaciones from './pages/operaciones'
import egresos from './components/egresos'
import ingresos from './components/ingresos'
import bandeja from './pages/bandeja'
import miCuenta from './pages/miCuenta'
import adminPanel from './pages/adminPanel'
import configValidacion from './components/configValidacion'
import agregarEgreso from './pages/agregarEgreso'
import agregarIngreso from './pages/agregarIngreso'

const routes = [
    {
        path: '/',
        component: panel,
        children: [
            {
                name: "index",
                path: '',
                component: inicio
            },
            {
                name: 'agregarEgreso',
                path: 'operaciones/egreso/agregar',
                component: agregarEgreso,
                meta: {
                    breadcrumb: [{
                        label: "Operaciones",
                        type: "fixed",
                        path: "/operaciones"
                    },
                    {
                        label: "Egresos",
                        type: "fixed",
                        path: "/operaciones/egreso"
                    },
                    {
                        label: "Agregar",
                        type: "fixed",
                        path: "/operaciones/egreso/agregar",
                        active: true
                    }]
                }
            },
            {
                name: 'agregarIngreso',
                path: 'operaciones/ingreso/agregar',
                component: agregarIngreso,
                meta: {
                    breadcrumb: [{
                        label: "Operaciones",
                        type: "fixed",
                        path: "/operaciones"
                    },
                    {
                        label: "Ingresos",
                        type: "fixed",
                        path: "/operaciones/ingreso"
                    },
                    {
                        label: "Agregar",
                        type: "fixed",
                        path: "/operaciones/ingreso/agregar",
                        active: true
                    }]
                }
            },
            {
                name: 'operaciones',
                path: 'operaciones',
                component: operaciones,
                meta: {
                    breadcrumb: [{
                        label: "Operaciones",
                        type: "fixed",
                        path: "/operaciones",
                        active: true
                    }]
                },
                children: [
                    {
                        name: 'egresos',
                        path: 'egreso',
                        component: egresos,
                        meta: {
                            breadcrumb: [{
                                label: "Operaciones",
                                type: "fixed",
                                path: "/operaciones"
                            },
                            {
                                label: "Egresos",
                                type: "fixed",
                                path: "/operaciones/egreso",
                                active: true
                            }]
                        },
                        children: [ 
                            {
                                name: 'egreso',
                                path: ':id',
                                component: egresos,
                                meta: {
                                    breadcrumb: [{
                                        label: "Operaciones",
                                        type: "fixed",
                                        path: "/operaciones"
                                    },
                                    {
                                        label: "Egresos",
                                        type: "fixed",
                                        path: "/operaciones/egreso"
                                    },
                                    {
                                        label: "id",
                                        type: "dynamic",
                                        dynamicText: "id",
                                        path: "/operaciones/egreso/id",
                                        active: true
                                    }]
                                }
                            }
                        ]
                    },
                    {
                        name: 'ingresos',
                        path: 'ingreso',
                        component: ingresos,
                        meta: {
                            breadcrumb: [{
                                label: "Operaciones",
                                type: "fixed",
                                path: "/operaciones"
                            },
                            {
                                label: "Ingresos",
                                type: "fixed",
                                path: "/operaciones/ingreso",
                                active: true
                            }]
                        }
                    }
                ],
            },
            {
                name: 'bandeja',
                path: 'bandeja',
                component: bandeja,
                meta: {
                    breadcrumb: [{
                        label: "Bandeja de Mensajes",
                        type: "fixed",
                        path: "/operaciones",
                        active: true
                    }]
                },
                children: [
                    {
                        name: 'mensaje',
                        path: ':id',
                        component: bandeja,
                        meta: {
                            breadcrumb: [{
                                label: "Bandeja de Mensajes",
                                type: "fixed",
                                path: "/bandeja"
                            },
                            {
                                label: "Mensaje id",
                                type: "dynamic",
                                dynamicText: "id",
                                path: "/bandeja/id",
                                active: true
                            }]
                        },
                    }
                ]
            },
            {
                name: 'cuenta',
                path: 'cuenta',
                component: miCuenta,
                meta: {
                    breadcrumb: [{
                        label: "Mi Cuenta",
                        type: "fixed",
                        path: "/cuenta",
                        active: true
                    }]
                }
            },
            {
                name: 'panel',
                path: 'panel',
                component: adminPanel,
                meta: {
                    breadcrumb: [{
                        label: "Admin Panel",
                        type: "fixed",
                        path: "/panel",
                        active: true
                    }]
                },
                children: [
                    {
                        name: 'configValidacion',
                        path: 'validaciones',
                        component: configValidacion,
                        meta: {
                            breadcrumb: [{
                                label: "Admin Panel",
                                type: "fixed",
                                path: "/panel"
                            },{
                                label: "Validacion de Egresos",
                                type: "fixed",
                                path: "/panel/validaciones",
                                active: true
                            }]
                        }
                    }
                ]
            }
        ]
    },
    {
        name: 'login',
        path: '/login',
        component: login
    },
    {
        name: 'error404',
        path: '/*',
        component: error404,
    },
    {
        name: 'error500',
        path: '/*',
        component: error500,
    }
];

var router = new VueRouter({
    routes: routes,
    mode: 'history'
});

router.beforeEach((to, from, next) => {
    if ((from.name == null || from.name == 'error404' || from.name == 'error500' || from.name == 'login') && to.name != 'error404' && to.name != 'error500' && to.name != 'login') {
        axios
            .get('/api/login')
            .then(response => {
                var data = response.data;
                if(data.code == 200) {
                    document.cookie = `username=${data.nombre}; path=/`;
                    document.cookie = `organizacion=${data.organizacion.razonSocial}; path=/`;
                    next();
                } else if (data.code == 403) {
                    next('/login');
                }
            })
            .catch(error => {
                console.log('error500')
                next({name: 'error500'});
            });
    } else if (to.name == 'login') {
        axios
            .get('/api/login')
            .then(response => {
                var data = response.data;
                if(data.code == 200) {
                    document.cookie = `username=${data.nombre}; path=/`;
                    document.cookie = `organizacion=${data.organizacion.razonSocial}; path=/`;
                    next('/');
                } else if (data.code == 403) {
                    next();
                }
            });
    } 
    else next()
})

export default router;