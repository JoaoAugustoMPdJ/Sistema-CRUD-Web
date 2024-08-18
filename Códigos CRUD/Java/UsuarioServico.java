package com.example.servico;

import com.example.modelo.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioServico {

    private List<Usuario> usuarios = new ArrayList<>();
    private AtomicLong contador = new AtomicLong();

    // Adicionar novo usuário
    public void adicionarUsuario(Usuario usuario) {
        usuario.setId(contador.incrementAndGet());
        usuarios.add(usuario);
    }

    // Buscar todos os usuários
    public List<Usuario> obterTodosUsuarios() {
        return usuarios;
    }

    // Buscar usuário por ID
    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst();
    }

    // Buscar usuário por email
    public Optional<Usuario> obterUsuarioPorEmail(String email) {
        return usuarios.stream().filter(usuario -> usuario.getEmail().equals(email)).findFirst();
    }

    // Atualizar usuário
    public void atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        obterUsuarioPorId(id).ifPresent(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setSenha(usuarioAtualizado.getSenha());
        });
    }

    // Deletar usuário
    public void deletarUsuario(Long id) {
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }
}

