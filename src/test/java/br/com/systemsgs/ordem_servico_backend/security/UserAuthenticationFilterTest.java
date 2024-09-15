package br.com.systemsgs.ordem_servico_backend.security;

import br.com.systemsgs.ordem_servico_backend.ConfigDadosEstaticosEntidades;
import br.com.systemsgs.ordem_servico_backend.exception.errors.TokenInexistenteException;
import br.com.systemsgs.ordem_servico_backend.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest
class UserAuthenticationFilterTest extends ConfigDadosEstaticosEntidades {

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserAuthenticationFilter userAuthenticationFilter;

    public UserAuthenticationFilterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Teste para retornar Token Inexistente")
    @Test
    public void testDoFilterInternalTokenInexistente() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        assertThrows(TokenInexistenteException.class, () -> {
            userAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });

        verify(filterChain, never()).doFilter(any(), any());
    }

    @DisplayName("Teste para retornar Token Inexistente porém Valido - Do Filter")
    @Test
    public void testDoFilterInternalTokenInexistentePorTokenInvalido() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN_TEST_INVALID);
        when(jwtTokenService.pegarToken(TOKEN_TEST_INVALID)).thenThrow(new RuntimeException());

        assertThrows(NoSuchElementException.class, () -> {
            userAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });

        verify(filterChain, never()).doFilter(any(), any());
    }
}