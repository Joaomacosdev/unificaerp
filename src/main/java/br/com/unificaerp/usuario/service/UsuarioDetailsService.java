package br.com.unificaerp.usuario.service;

import br.com.unificaerp.infra.exception.MsgApiException;
import br.com.unificaerp.infra.security.JwtService;
import br.com.unificaerp.usuario.model.Usuario;
import br.com.unificaerp.usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.buscaPorLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado no banco de dados.");
        }


        if(!usuario.isEnabled()) {
            throw new MsgApiException("Usuário bloqueado, entre em contato com o administrador do sistema.", HttpStatus.UNAUTHORIZED);
        }

        if(usuario.getEmpresa().getBloqueio()) {
            throw new MsgApiException("Empresa bloqueada, entre em contato com o administrador do sistema.", HttpStatus.UNAUTHORIZED);
        }

        return usuario;
    }


    public void existsByTokenParaUser(String token) {
        Long idEmpresa= jwtService.extrairEmpresaId(token);
        Long idUsuario  = jwtService.extrairUsuarioId(token);

        boolean existe = usuarioRepository.existsByTokenParaUser(token, idUsuario, idEmpresa);

        if(!existe) {
            throw new MsgApiException("Usuário não autenticado ou token inválido.", HttpStatus.UNAUTHORIZED);
        }
    }
}
