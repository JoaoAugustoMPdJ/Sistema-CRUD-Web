package com.example.controlador;

import com.example.modelo.Usuario;
import com.example.servico.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServico usuarioServico;

    @GetMapping("/login")
    public String exibirPaginaLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUsuario(@RequestParam String email, @RequestParam String senha, Model modelo) {
        Optional<Usuario> usuarioExistente = usuarioServico.obterUsuarioPorEmail(email);
        if (usuarioExistente.isPresent() && usuarioExistente.get().getSenha().equals(senha)) {
            return "redirect:/usuarios/lista";
        }
        modelo.addAttribute("erro", "Credenciais inválidas");
        return "login";
    }

    @GetMapping("/cadastro")
    public String exibirPaginaCadastro(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario, Model modelo) {
        if (usuarioServico.obterUsuarioPorEmail(usuario.getEmail()).isPresent()) {
            modelo.addAttribute("erro", "Email já registrado");
            return "cadastro";
        }
        usuarioServico.adicionarUsuario(usuario);
        return "redirect:/usuarios/login";
    }

    @GetMapping("/lista")
    public String listarUsuarios(Model modelo) {
        modelo.addAttribute("usuarios", usuarioServico.obterTodosUsuarios());
        return "usuario-lista";
    }

    @GetMapping("/editar/{id}")
    public String exibirPaginaEdicao(@PathVariable Long id, Model modelo) {
        Optional<Usuario> usuarioOpt = usuarioServico.obterUsuarioPorId(id);
        if (usuarioOpt.isPresent()) {
            modelo.addAttribute("usuario", usuarioOpt.get());
            return "usuario-editar";
        }
        return "redirect:/usuarios/lista";
    }

    @PostMapping("/editar/{id}")
    public String atualizarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario) {
        usuarioServico.atualizarUsuario(id, usuario);
        return "redirect:/usuarios/lista";
    }

    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        usuarioServico.deletarUsuario(id);
        return "redirect:/usuarios/lista";
    }
}
