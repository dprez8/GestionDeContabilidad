import VueRouter from 'vue-router'
import {RequestHelper} from './util/utils'
import jwt_decode from "jwt-decode";

import {  
    panel, login, error404, error401, error500, inicio,
    operaciones, egresos, ingresos, presupuestos, agregarEgreso, agregarIngreso, bandeja, 
    miCuenta, configValidacion
} from './pages'

const routes = [
    {
        name: 'panel',
        path: '/',
        component: panel,
        props: {
            sidebar: [
                {text: 'Operaciones', icon: 'journal-check', to: '/operaciones'},
                {text: 'Presupuestos', icon: 'journals', to: '/presupuestos'},
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
                name: 'presupuestos',
                path: 'presupuestos',
                component: presupuestos,
                meta: {
                    breadcrumb: [{
                        label: "Presupuestos",
                        type: "fixed",
                        path: "/presupuestos",
                        active: true
                    }]
                }
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
                {text: 'Vinculacion automática', icon: 'gear-wide-connected', to: '/admin/vinculacion'},
                {text: 'Validacion de Egresos', icon: 'clock', to: '/admin/validaciones'}
            ]
        },
        children: [
            {
                name: 'configValidacion',
                path: 'validaciones',
                component: configValidacion,
                meta: {
                    breadcrumb: [
                    {
                        label: "Validacion de Egresos",
                        type: "fixed",
                        path: "/admin/validaciones",
                        active: true
                    }]
                }
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
    var token = sessionStorage.getItem('token');
    var tokenDecoded = jwt_decode(token);

    switch (to.matched[0].name) {
        case 'panel':
            // Si ya estaba navegando dentro de la app no me saques
            if(from.matched.length && from.matched[0].name == 'panel') {
                next();
                break;
            }

            // Pero si estoy entrando desde 0...

            // Si no tengo ningún token guardado mandame al login
            if(token) {              
                if(tokenDecoded.rol == "Estandar") {
                    next();
                }
                else {
                    next({name: 'unauthorized'})
                }
            } else {
                next('/login');
            }
            break;

        case 'adminPanel':
            // Si ya estaba navegando dentro de la app no me saques
            if(from.matched.length && from.matched[0].name == 'adminPanel') {
                next();
                break;
            }

            // Pero si estoy entrando desde 0...

            // Si no tengo ningún token guardado mandame al login
            if(token) {
                if(tokenDecoded.rol == "Admin") {
                    next();
                }
                else {
                    next({name: 'unauthorized'})
                }
            } else {
                next('/login');
            }
            break;

        case 'login':
            // Si ya tengo un token, mandame al panel
            if(token) {
                next('/');
            } else {
                next();
            }
            break;
    
        default:
            next();
            break;
    }
});

export default router;