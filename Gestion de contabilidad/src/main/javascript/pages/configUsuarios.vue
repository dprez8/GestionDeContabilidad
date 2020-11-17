<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="p-4 d-flex justify-content-center" style="background: #eee; min-height: 100%">
            <div class="w-100" style="max-width: 768px;">
                <template v-for="(usuario, key) in usuarios">
                    <b-card :key="key" no-body class="overflow-hidden mb-4">
                        <b-row no-gutters>
                            <b-col md="2" class="d-flex justify-content-center align-items-center p-2 user-card-skew">
                                <b-avatar size="5rem" variant="secondary"></b-avatar>
                            </b-col>
                            <b-col>
                                <b-card-body :title="`${usuario.nombre} ${usuario.apellido}`" :sub-title="`Usuario ${usuario.tipo}`">
                                    <b-card-text>
                                        <div><b-icon-person class="mr-2"/>{{usuario.username}}</div>
                                        <div><b-icon-bar-chart class="mr-2"/><b-link to="/admin/entidades">Entidad</b-link></div>
                                    </b-card-text>
                                </b-card-body>
                            </b-col>
                        </b-row>
                    </b-card>
                </template>
            </div>
        </div>
    </b-overlay>
</template>

<script>
export default {
    data() {
        return {
            usuarios: [
                {
                    id: 1,
                    nombre: "Alejandro",
                    apellido: "Roco",
                    username: "aroco",
                    tipo: "Estandar"
                },
                {
                    id: 2,
                    nombre: "Rocío",
                    apellido: "Rojas",
                    username: "rrojas",
                    tipo: "Estandar"
                },
                {
                    id: 3,
                    nombre: "Julieta",
                    apellido: "Azul",
                    username: "jazul",
                    tipo: "Estandar"
                }
            ],
            loading: false
        }
    }
}
</script>

<style>
.user-card-skew::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: var(--primary);
    clip-path: polygon(0% 0%, 75% 0%, 25% 100%, 0% 100%);
    transition: all .2s;
    animation: .5s intro-user;
}
@keyframes intro-user {
    from {
        clip-path: polygon(0% 0%, 100% 0%, 100% 100%, 0% 100%);
    }
    to {
        clip-path: polygon(0% 0%, 75% 0%, 25% 100%, 0% 100%);
    }
}
</style>