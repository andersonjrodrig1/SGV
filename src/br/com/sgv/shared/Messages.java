package br.com.sgv.shared;

/**
 * @author Anderson Junior Rodrigues
 */
public class Messages {
    
    /* General */
    public static String title_presentation = "Sitema de Gerenciamento de Vendas";
    public static String fail_save = "Falha ao salvar os dados!";
    public static String fail_find = "Falha ao buscar os dados!";
    public static String fail_remove = "Falha ao excluir os dados!";
    public static String save_success = "Salvo com sucesso!";
    public static String fail_update = "Falha ao atualizar!";
    public static String remove_sucess = "Excluido com sucesso!";
    public static String update_sucess = "Atualizado com sucesso!";
    public static String data_inconsistent = "Dados inconsistentes, \nnão foi possível fazer o login.";
    public static String negative_acess = "Usuário não possui permissão de acesso à tela.";
    public static String select_row = "Selecione um item para executar está ação.";
    public static String remove_modal = "Deseja excluir o item selecionado?";
    public static String not_found = "Item não encontrado!";
    public static String select_product = "Favor selecione um item na lista de produtos.";
    public static String required_product_key = "Necessário informar código do produto.";
    
    /* Login */
    public static String user = "Usuário..:";
    public static String password = "Senha..:";
    public static String user_not_found = "Usuário e/ou senha inválida!";
    
    /* Modal logout*/
    public static String login = "Entrar no sistema";
    public static String logout_system = "Deseja sair do sistema?";
    
    /* About */
    public static String title_sobre = "Sistema de Gerenciamento de Vendas";
    public static String version = "versão 1.0.0";
    public static String text_dev = "Desenvolvido em Java";
    public static String text_db = "Banco de Dados: MySQL";
    public static String text_copyright = "Copyright © 2018 Du Rei LTDA";
    public static String text_right = "Todos os Direitos Reservados.";
    
    /* Register User */
    public static String name_required = "Nome obrigatório!";
    public static String name_complete = "Nome inválido!";
    public static String user_required = "Usuário obrigatório!";
    public static String password_required = "Senha obrigatória!";
    public static String password_format = "Senha deve ter entre 4 e 10 caracteres!";
    public static String password_equals = "Confirmação senha e senha informada devem ser iguais!";
    
    /* List User */
    public static String title_list_user = "Lista de Usuários Cadastrados";
    
    /* Register Product */
    public static String key_register_product = "Código Produto obrigatório.";
    public static String name_register_product = "Nome do Produto obrigatório.";
    public static String value_register_product = "Valor do Produto obrigatório.";
    public static String measure_register_product = "Volume do Produto obrigatório.";
    public static String verif_value_field = "Valor informado inválido.\nDigite apenas números.";
    public static String title_register_product = "Cadastro de Produto";
    public static String title_update_product = "Atualizar Produto";
    public static String btn_register_product = "Cadastrar";
    public static String btn_update_product = "Atualizar";
    public static String amount_required = "Quantidade obrigatório.";
    
    /* Register Measure */
    public static String name_register_measure = "Nome da Unidade obrigatório.";
    public static String calc_register_measure = "Tipo de Calculo obrigatório.";
    public static String initials_register_measure = "Sigla não pode conter mais de 2 caracteres.";
    
    /* Status Register */
    public static String status_register = "Registro de saída não pode ser excluida.\nTotalização já realizada.";
    
    /* Checkout */
    public static String checkout_name = "Descrição de saída obrigatório.";
    public static String checkout_value = "Total de saída obrigatório.";
    public static String checkout_date = "Data da saída obrigatório.";
    public static String checkout_no_remove = "Registro de saída não pode ser removido!\nRegistro já contabilizado no fechamento.";
}
