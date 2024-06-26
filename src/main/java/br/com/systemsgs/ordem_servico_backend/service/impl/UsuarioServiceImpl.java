package br.com.systemsgs.ordem_servico_backend.service.impl;

import br.com.systemsgs.ordem_servico_backend.dto.ModelUserDTO;
import br.com.systemsgs.ordem_servico_backend.exception.UsuarioNaoEncontradoException;
import br.com.systemsgs.ordem_servico_backend.model.ModelUser;
import br.com.systemsgs.ordem_servico_backend.repository.UsuarioRepository;
import br.com.systemsgs.ordem_servico_backend.service.UsuarioService;
import br.com.systemsgs.ordem_servico_backend.util.UtilUsuarios;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UtilUsuarios utilUsuarios;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ModelUser pesquisaPorId(Long id) {
        Optional<ModelUser> modelUsuario = usuarioRepository.findById(id);
        return modelUsuario.orElseThrow(() -> new UsuarioNaoEncontradoException());
    }

    @Override
    public List<ModelUser> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public ModelUser salvarUsuarios(ModelUserDTO modelUsuariosDTO) {
        ModelUser usuarioConvertido = mapper.map(modelUsuariosDTO, ModelUser.class);
        return usuarioRepository.save(usuarioConvertido);
    }

    @Override
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public ModelUser updateUsuarios(Long id, ModelUserDTO modelUsuariosDTO) {
        ModelUser usuarioPesquisado = utilUsuarios.pesquisarUsuarioPeloId(id);
        mapper.map(modelUsuariosDTO, ModelUser.class);
        BeanUtils.copyProperties(modelUsuariosDTO, usuarioPesquisado, "id");

        return usuarioRepository.save(usuarioPesquisado);
    }
}
