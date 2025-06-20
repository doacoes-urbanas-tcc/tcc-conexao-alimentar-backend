# 📋 Regras de Negócio

Este documento tem como objetivo definir as regras de negócio do nosso software. As regras aqui descritas orientam os processos internos e padronizam práticas essenciais para o bom funcionamento da organização, garantindo que todos sigam as mesmas diretrizes.

---

### RN001

- **Nome:** Registro único  
- **Data de Criação:** 27/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Todos os usuários do sistema só poderão se cadastrar uma vez, isso é válido tanto para os estabelecimentos que se cadastrarem quanto para as ONGs.  
- **Justificativa:** Garante a integridade e segurança da plataforma, evita a duplicidade de dados e evita fraudes.  
- **Atores envolvidos:**  
  - ONGs  
  - Estabelecimentos comerciais  
  - Administradores  
- **Condições de acionamento:**  
  - Tentativa de cadastro com CNPJ já cadastrado no sistema  
- **Consequências de violação:**  
  - Cadastro será recusado automaticamente pelo sistema  

---

### RN002

- **Nome:** Tempo limite de solicitação  
- **Data de Criação:** 27/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Após a solicitação de doação ter sido efetuada, o estabelecimento comercial tem um tempo limite de 48 horas para responder. Caso contrário, a solicitação será automaticamente cancelada.  
- **Justificativa:** Torna o processo de doação mais ágil, considerando que o sistema é voltado principalmente para alimentos perecíveis.  
- **Atores envolvidos:**  
  - Estabelecimentos comerciais  
  - ONGs  
  - Voluntários  
- **Condições de acionamento:**  
  - Após solicitação de doação  
- **Consequências de violação:**  
  - Solicitação cancelada automaticamente pelo sistema  

---

### RN003

- **Nome:** Tipo de voluntariado obrigatório  
- **Data de Criação:** 28/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Todo voluntário deverá selecionar ao menos um tipo de voluntariado ao se cadastrar.  
- **Justificativa:** Garante o direcionamento adequado das atividades e organização das tarefas.  
- **Atores envolvidos:**  
  - Voluntários  
  - Administradores  
- **Condições de acionamento:**  
  - Cadastro ou atualização de perfil de voluntário  
- **Consequências de violação:**  
  - Cadastro não será finalizado  

---

### RN004

- **Nome:** Veículo vinculado ao tipo de voluntariado  
- **Data de Criação:** 28/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Voluntários que optarem por atuar como transportadores deverão cadastrar informações básicas do veículo.  
- **Justificativa:** Permite controle e logística eficaz nas entregas.  
- **Atores envolvidos:**  
  - Voluntários  
  - Administradores  
- **Condições de acionamento:**  
  - Escolha do tipo de voluntariado: transporte  
- **Consequências de violação:**  
  - Cadastro não será concluído sem os dados do veículo  

---

### RN005

- **Nome:** Doação não editável após confirmação  
- **Data de Criação:** 28/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Após confirmação da doação por uma ONG ou voluntário, os dados da doação não poderão ser alterados.  
- **Justificativa:** Garante integridade das informações e segurança no processo de doação.  
- **Atores envolvidos:**  
  - Estabelecimentos comerciais  
  - ONGs  
  - Voluntários  
- **Condições de acionamento:**  
  - Confirmação de doação  
- **Consequências de violação:**  
  - Edição será bloqueada pelo sistema  

---

### RN006

- **Nome:** Cancelamento de doação com justificativa  
- **Data de Criação:** 28/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Para cancelar uma doação após confirmação, é obrigatória uma justificativa.  
- **Justificativa:** Garante rastreabilidade e evita cancelamentos injustificados.  
- **Atores envolvidos:**  
  - ONGs  
  - Voluntários  
  - Estabelecimentos comerciais  
- **Condições de acionamento:**  
  - Cancelamento de doação confirmada  
- **Consequências de violação:**  
  - Cancelamento não será aceito sem justificativa  

---

### RN007

- **Nome:** Histórico de doações por usuário  
- **Data de Criação:** 31/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Todos os usuários têm acesso ao seu próprio histórico de doações.  
- **Justificativa:** Permite controle e transparência das ações realizadas.  
- **Atores envolvidos:**  
  - ONGs  
  - Voluntários  
  - Estabelecimentos comerciais  
- **Condições de acionamento:**  
  - Acesso ao perfil  
- **Consequências de violação:**  
  - Usuário perde acesso ao próprio histórico de ações  

---

### RN008

- **Nome:** Notificação automática de nova doação  
- **Data de Criação:** 31/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Ao cadastrar uma nova doação, ONGs e voluntários da região serão notificados.  
- **Justificativa:** Aumenta a eficiência do sistema e evita o desperdício de alimentos.  
- **Atores envolvidos:**  
  - ONGs  
  - Voluntários  
- **Condições de acionamento:**  
  - Cadastro de nova doação  
- **Consequências de violação:**  
  - Demora na retirada dos alimentos  

---

### RN009

- **Nome:** Aprovação de cadastro por administrador  
- **Data de Criação:** 31/05/2025  
- **Autor:** Lívia Neves  
- **Descrição:** ONGs e estabelecimentos só terão acesso ao sistema após aprovação de cadastro pelo administrador.  
- **Justificativa:** Garante credibilidade das entidades cadastradas.  
- **Atores envolvidos:**  
  - Administradores  
  - ONGs  
  - Estabelecimentos comerciais  
- **Condições de acionamento:**  
  - Novo cadastro de ONG ou estabelecimento  
- **Consequências de violação:**  
  - Entidades sem verificação terão acesso indevido ao sistema  

---

### RN010

- **Nome:** Doação com data de validade obrigatória  
- **Data de Criação:** 01/06/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Toda doação de alimento exige o preenchimento da data de validade.  
- **Justificativa:** Garante segurança alimentar aos beneficiados.  
- **Atores envolvidos:**  
  - Estabelecimentos comerciais  
  - Administradores  
- **Condições de acionamento:**  
  - Cadastro de doação  
- **Consequências de violação:**  
  - Cadastro será bloqueado até preenchimento correto  

---

### RN011

- **Nome:** Limite de visualização por localização  
- **Data de Criação:** 01/06/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Usuários só visualizarão doações disponíveis em sua área de atuação.  
- **Justificativa:** Otimiza logística e evita solicitações inviáveis.  
- **Atores envolvidos:**  
  - ONGs  
  - Voluntários  
- **Condições de acionamento:**  
  - Acesso à lista de doações  
- **Consequências de violação:**  
  - Visualização de doações inadequadas à região  

---

### RN012

- **Nome:** Atribuição de doação a um único destinatário  
- **Data de Criação:** 01/06/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Uma vez aceita, a doação é bloqueada para outros usuários até conclusão ou cancelamento.  
- **Justificativa:** Evita conflitos e duplicidade.  
- **Atores envolvidos:**  
  - ONGs  
  - Voluntários  
  - Estabelecimentos comerciais  
- **Condições de acionamento:**  
  - Aceitação de doação  
- **Consequências de violação:**  
  - Doação pode ser erroneamente reservada para múltiplos usuários  

---

### RN013

- **Nome:** Avaliação de doações  
- **Data de Criação:** 01/06/2025  
- **Autor:** Lívia Neves  
- **Descrição:** Após a entrega, as partes envolvidas podem avaliar a doação com nota e comentário.  
- **Justificativa:** Melhora a transparência e permite o controle de qualidade das interações.  
- **Atores envolvidos:**  
  - ONGs  
  - Voluntários  
  - Estabelecimentos comerciais  
- **Condições de acionamento:**  
  - Finalização de doação  
- **Consequências de violação:**  
  - Falta de feedback e risco de manter más práticas não registradas  


### RN014

- **Nome:** Validação de retirada
- **Data de Criação:** 01/06/2025
- **Autor:** Lívia Neves 
- **Descrição:** A retirada/entrega da doação só será validada mediante a leitura do QrCode do doador por parte da organização receptora, para que não haja qualquer tipo de fraude ou falsa doação e nenhuma das partes seja lesada.

---


### RN015

- **Nome:** Horário de doações
- **Data de Criação:** 01/06/2025
- **Autor:** Lívia Neves
- **Descrição:** As doações só poderão ser efetuadas de 08:00 às 18:00, essa medida torna o processo de doação mais 

---

