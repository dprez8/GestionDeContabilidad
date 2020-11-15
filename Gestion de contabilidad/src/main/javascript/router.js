import VueRouter from 'vue-router'
import {RequestHelper} from './util/utils'
import jwt_decode from "jwt-decode";

import {  
    panel, login, error404, error401, error500, inicio,
    operaciones, egresos, ingresos, agregarEgreso, agregarIngreso, bandeja, 
    miCuenta, configValidacion, configCriterios, configUsuarios, configEntidades, configVinculacion
} from './pages'

const routes = [
    {
        name: 'panel',
        path: '/',
        component: panel,
        props: {
            sidebar: [
                {text: 'Operaciones', icon: 'journal-check', to: '/operaciones'},
                {text: 'Bandeja de Mensajes', icon: 'chat-left-text', to: '/bandeja'}
            ]
        },
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
                        path: "/bandeja",
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
            }
        ]
    },
    {
        name: 'adminPanel',
        path: '/admin',
        component: panel,
        props: {
            sidebar: [
                {text: 'Usuarios', icon: 'person-lines-fill', to: '/admin/usuarios'},
                {text: 'Entidades', icon: 'bar-chart-fill', to: '/admin/entidades'},
                {text: 'Criterios', icon: 'ui-checks', to: '/admin/criterios'},
                {text: 'Vinculación automática', icon: 'gear-wide-connected', to: '/admin/vinculacion'},
                {text: 'Validación de Egresos', icon: 'clock', to: '/admin/validaciones'}
            ]
        },
        children: [
            {
                name: 'configUsuarios',
                path: 'usuarios',
                component: configUsuarios,
                meta: {
                    breadcrumb: [{
                        label: "Usuarios",
                        type: "fixed",
                        path: "/admin/usuarios",
                        active: true
                    }]
                }
            },
            {
                name: 'configEntidades',
                path: 'entidades',
                component: configEntidades,
                meta: {
                    breadcrumb: [{
                        label: "Entidades",
                        type: "fixed",
                        path: "/admin/entidades",
                        active: true
                    }]
                }
            },
            {
                name: 'configCriterios',
                path: 'criterios',
                component: configCriterios,
                meta: {
                    breadcrumb: [{
                        label: "Criterios",
                        type: "fixed",
                        path: "/admin/criterios",
                        active: true
                    }]
                }
            },
            {
                name: 'configVinculacion',
                path: 'vinculacion',
                component: configVinculacion,
                meta: {
                    breadcrumb: [
                    {
                        label: "Vinculación automática",
                        type: "fixed",
                        path: "/admin/vinculacion",
                        active: true
                    }]
                }
            },
            {
                name: 'configValidacion',
                path: 'validaciones',
                component: configValidacion,
                meta: {
                    breadcrumb: [
                    {
                        label: "Validación de Egresos",
                        type: "fixed",
                        path: "/admin/validaciones",
                        active: true
                    }]
                }
            },
            {
                name: 'adminCuenta',
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
            }
        ]
    },
    {
        name: 'login',
        path: '/login',
        component: login
    },
    {
        name: 'notFound',
        path: '/*',
        component: error404,
    },
    {
        name: 'unauthorized',
        path: '',
        component: error401,
    },
    {
        name: 'serverError',
        path: '',
        component: error500,
    }
];

var router = new VueRouter({
    routes: routes,
    mode: 'history'
});

router.beforeEach((to, from, next) => {
    if(to.name == 'unauthorized' || to.name == 'notFound' || to.name == 'serverError' || to.name == 'login') {
        next();
        return;
    }

    var token = sessionStorage.getItem('token');
    var tokenDecoded = null;

    if(token)
        tokenDecoded = jwt_decode(token);
    else {
        next('/login');
        return;
    }

    var goingTo = to.matched[0].name;
    var comingFrom = null;

    if(from.matched.length)
        comingFrom = from.matched[0].name;

    // Si ya estaba navegando en la misma página dejame seguir navegando
    if(goingTo == comingFrom) {
        next();
    } else if(authorized(goingTo, tokenDecoded)) {
        next();
    } else {
        next({name: 'unauthorized'});
    }

    function authorized(goingTo, tokenDecoded) {
        return  (goingTo == 'panel' && tokenDecoded.rol == 'Estandar') || 
                (goingTo == 'adminPanel' && tokenDecoded.rol == 'Estandar');
    }

});

export default router;