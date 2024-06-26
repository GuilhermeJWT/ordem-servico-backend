package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.exception.EmailDuplicadoException;
import br.com.systemsgs.ordem_servico_backend.exception.UsuarioNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelUser;
import br.com.systemsgs.ordem_servico_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ModelUser pesquisarUsuarioPeloId(Long id){
        ModelUser pesquisaUsuario = usuarioRepository.findById(id).
                orElseThrow(() -> new UsuarioNaoEncontradoException());

        return pesquisaUsuario;
    }

    public void validaEmailDuplicado(String email){
        if(usuarioRepository.findByEmail(email).isPresent()) {
            throw new EmailDuplicadoException();
        }
    }
}
