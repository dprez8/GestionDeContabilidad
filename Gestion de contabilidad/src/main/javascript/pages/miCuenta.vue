<template>
    <div class="p-4 d-flex justify-content-center" style="background: #eee">
        <div class="w-100" style="max-width: 768px;">
            <b-card title="Datos personales" class="mb-4">
                <div class="mt-2">Nombre</div>
                <b-form-input value="Javier" readonly></b-form-input>
                <div class="mt-2">Email</div>
                <b-form-input value="javiersito@gmail.com" readonly></b-form-input>
            </b-card>
            <b-card title="Datos de organizacion" class="mb-4">
                <div class="mt-2">Razon social</div>
                <b-form-input value="Entidad Juridica" readonly></b-form-input>
                <div class="mt-2">CUIT</div>
                <b-form-input value="1234" readonly></b-form-input>
                <div class="mt-2">Nombre Ficticio</div>
                <b-form-input value="Organizacion" readonly></b-form-input>
            </b-card>
            <b-card title="Cambiar contraseña">
                <div class="mt-2">Contraseña actual</div>
                <b-form-input type="password" placeholder="Ingrese su contraseña"></b-form-input>
                <div class="mt-2">Nueva contraseña</div>
                <b-form-input type="password" placeholder="Ingrese nueva contraseña"></b-form-input>
                <div class="mt-2">Repita la nueva contraseña</div>
                <b-form-input type="password" placeholder="Repita contraseña"></b-form-input>
                <div class="mt-2">
                    <b-button variant="primary">Confirmar</b-button>
                </div>
            </b-card>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style>

</style>