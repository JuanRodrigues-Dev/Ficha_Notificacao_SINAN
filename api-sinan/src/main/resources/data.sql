-- Dados pessoais
INSERT INTO dados_pessoais (id, nome_paciente, data_nascimento, sexo, gestante, nome_mae) VALUES
                                                                                              (1, 'Maria da Silva', '1990-05-10', 'F', 'NAO', 'Ana da Silva'),
                                                                                              (2, 'maria   da silva', '1990-05-10', 'F', 'NAO', 'ANA DA SILVA'),
                                                                                              (3, 'Joao Pereira', '1985-03-20', 'M', 'NAO_SE_APLICA', 'Rita Pereira');

-- Dados de residência
INSERT INTO dados_residencia (id, uf_residencia, municipio_residencia) VALUES
                                                                           (1, 'PB', 'Cajazeiras'),
                                                                           (2, 'PB', 'Cajazeiras'),
                                                                           (3, 'PB', 'Sousa');

-- Notificações (referenciando os ids acima)
INSERT INTO notificacao (id, agravo, data_notificacao, uf_notificacao, municipio_notificacao, unidade_saude, dados_pessoais_id, dados_residencia_id) VALUES
                                                                                                                                                         (1, 'Dengue', '2026-09-10', 'PB', 'Cajazeiras', 'UPA Central', 1, 1),
                                                                                                                                                         (2, 'Dengue', '2026-09-12', 'PB', 'Cajazeiras', 'Hospital Regional', 2, 2),
                                                                                                                                                         (3, 'Chikungunya', '2026-09-15', 'PB', 'Sousa', 'UBS Sousa', 3, 3);