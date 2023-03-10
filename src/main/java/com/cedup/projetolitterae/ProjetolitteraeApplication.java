package com.cedup.projetolitterae;

import com.cedup.projetolitterae.backend.dto.LivroBibliotecaDto;
import com.cedup.projetolitterae.backend.entities.Biblioteca;
import com.cedup.projetolitterae.backend.entities.Endereco;
import com.cedup.projetolitterae.backend.entities.Livro;
import com.cedup.projetolitterae.backend.entities.LivroBiblioteca;
import com.cedup.projetolitterae.backend.entities.Locacao;
import com.cedup.projetolitterae.backend.entities.Notificacao;
import com.cedup.projetolitterae.backend.entities.Resenha;
import com.cedup.projetolitterae.backend.entities.UltimoId;
import com.cedup.projetolitterae.backend.entities.Usuario;
import com.cedup.projetolitterae.backend.enums.GeneroLivro;
import com.cedup.projetolitterae.backend.enums.StatusLocacao;
import com.cedup.projetolitterae.backend.enums.TipoPerfil;
import com.cedup.projetolitterae.backend.repositories.BibliotecaRepository;
import com.cedup.projetolitterae.backend.repositories.EnderecoRepository;
import com.cedup.projetolitterae.backend.repositories.LivroBibliotecaRepository;
import com.cedup.projetolitterae.backend.repositories.LivroRepository;
import com.cedup.projetolitterae.backend.repositories.LocacaoRepository;
import com.cedup.projetolitterae.backend.repositories.NotificacaoRepository;
import com.cedup.projetolitterae.backend.repositories.ResenhaRepository;
import com.cedup.projetolitterae.backend.repositories.UltimoIdRepository;
import com.cedup.projetolitterae.backend.repositories.UsuarioRepository;
import com.cedup.projetolitterae.backend.services.LivroBibliotecaService;
import com.cedup.projetolitterae.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class ProjetolitteraeApplication implements CommandLineRunner {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	EnderecoRepository enderecoRepository;
	@Autowired
	BibliotecaRepository bibliotecaRepository;
	@Autowired
	LivroRepository livroRepository;
	@Autowired
	ResenhaRepository resenhaRepository;
	@Autowired
	LivroBibliotecaRepository livroBibliotecaRepository;
	@Autowired
	LocacaoRepository locacaoRepository;
	@Autowired
	UltimoIdRepository ultimoIdRepository;
	@Autowired
	NotificacaoRepository notificacaoRepository;

	@Bean
	public String getDirBaseArquivo(@Value( "${spring.web.resources.static-locations:/litterae/files}" )
										List<String> diratorioStatic){
		if(CollectionUtils.isEmpty(diratorioStatic)){
			throw new RuntimeException("Pasta de arquivos n??o definida");
		}
		File diratorioPadraoArquivos = new File(diratorioStatic.get(0).replace("file:",""));
		if(!diratorioPadraoArquivos.exists()){
			diratorioPadraoArquivos.mkdirs();
		}
		return diratorioPadraoArquivos.getAbsolutePath();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjetolitteraeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeDatabase();
	}

	private void initializeDatabase(){
		gerarBase1();

		Biblioteca litterae = new Biblioteca("", "Litteae", "",
				"", TipoPerfil.MASTER, "1234", true, null,null);
		litterae.setEnderecoBiblioteca(null);
		litterae.setId(1L);
		bibliotecaRepository.save(litterae);

		UltimoId ultimoIdBiblioteca = new UltimoId(1, "biblioteca", 100001L);
		UltimoId ultimoIdUsuario = new UltimoId(2, "usuario", 100004L);
		ultimoIdRepository.saveAll(List.of(ultimoIdBiblioteca, ultimoIdUsuario));
	}

	private void gerarBase1(){
		//ENDERECO BIBLIOTECA
		Endereco e1 = new Endereco("123489", "SC", "Blumenau", "Velha Central", "Rua Rio Branco",
				"87", "Ao lado do posto.");

		//CADASTRO DE BIBLIOTECAS
		Biblioteca b1 = new Biblioteca("91.024.520/0001-90", "Cheiro de livro", "cheirodelivro@email.com",
				"45987412365",  TipoPerfil.ADMIN, "1234", true, 10.00,2.00);
		b1.setEnderecoBiblioteca(e1);
		b1.setId(100001L);

		//CADASTRO DE LIVROS
		Livro l1 = new Livro("Rainha Vermelha","Victoria aveyard", "Mare e sua fam??lia s??o vermelhos: " +
				"plebeus, humildes, destinados a servir uma elite prateada cujos poderes sobrenaturais os tornam quase deuses. " +
				"Mare rouba o que pode para ajudar sua fam??lia a sobreviver e n??o tem esperan??as de escapar do vilarejo " +
				"miser??vel onde mora.",
				"PT", "1", "Seguinte", "8565765695", "Padr??o", new Date(2015-1900,6-1,9));
		l1.setGeneros(List.of(GeneroLivro.FICCAO_CIENTIFICA, GeneroLivro.ACAO_E_AVENTURA));
		l1.setImagem("/livro/1/a3a3b0c312ea2497220fbc12f53b945b.jpg");

		Livro l2 = new Livro("Espada de Vidro","Victoria aveyard","O sangue de Mare Barrow ?? vermelho, " +
				"da mesma cor da popula????o comum, mas sua habilidade de controlar a eletricidade a torna t??o poderosa quanto " +
				"os membros da elite de sangue prateado. Depois que essa revela????o foi feita em rede nacional, Mare se " +
				"transformou numa arma perigosa que a corte real quer esconder e controlar.",
				"PT", "1", "Seguinte", "8565765946", "Padr??o", new Date(2016-1900,2-1,4));
		l2.setGeneros(List.of(GeneroLivro.FICCAO_CIENTIFICA, GeneroLivro.ACAO_E_AVENTURA));
		l2.setImagem("/livro/2/41PHjMF81IL.jpg");

		Livro l3 = new Livro("A Sombra do Vento","Lu??s Carlos Zaf??n","Junto com seu amigo Ferm??n, Daniel " +
				"percorre a cidade, adentrando as ruelas e os segredos mais obscuros de Barcelona. Anos se passam e sua " +
				"investiga????o inocente se transforma em uma trama de mist??rio, magia, loucura e assassinato. E o destino " +
				"de seu autor favorito de repente parece intimamente conectado ao dele.",
				"PT", "1", "Suma", "9788556510341", "Padr??o", new Date(2017-1900,5-1,26));
		l3.setGeneros(List.of(GeneroLivro.ROMANCE, GeneroLivro.FICCAO_CIENTIFICA));
		l3.setImagem("/livro/3/91xOzA3VHtL.jpg");

		Livro l4 = new Livro("Jogos Vorazes","Suzanne Collins", "Quando Katniss Everdeen, de 16 anos, " +
				"decide participar dos Jogos Vorazes para poupar a irm?? mais nova, causando grande como????o no pa??s, ela " +
				"sabe que essa pode ser a sua senten??a de morte. Mas a jovem usa toda a sua habilidade de ca??a e sobreviv??ncia" +
				" ao ar livre para se manter viva.",
				"PT", "14", "Rocco", "978-65-5532-073-2", "Padr??o", new Date(2008-1900,2-1,19));
		l4.setGeneros(List.of(GeneroLivro.FICCAO_CIENTIFICA, GeneroLivro.ACAO_E_AVENTURA));
		l4.setImagem("/livro/4/jogosvorazes.jpg");

		Livro l5 = new Livro("Os Sete Maridos de Evelyn Hugo","Taylor Jenkins", "Lend??ria estrela de Hollywood, " +
				"Evelyn Hugo sempre esteve sob os holofotes ??? seja estrelando uma produ????o vencedora do Oscar, protagonizando " +
				"algum esc??ndalo ou aparecendo com um novo marido??? pela s??tima vez. Agora, prestes a completar oitenta anos " +
				"e reclusa em seu apartamento no Upper East Side, a famigerada atriz decide contar a pr??pria hist??ria ??? " +
				"ou sua ???verdadeira hist??ria??? ???, mas com uma condi????o: que Monique Grant, jornalista iniciante e at?? ent??o " +
				"desconhecida, seja a entrevistadora. Ao embarcar nessa misteriosa empreitada, a jovem rep??rter come??a a " +
				"se dar conta de que nada ?? por acaso ??? e que suas trajet??rias podem estar profunda e irreversivelmente conectadas.",
				"PT", "14", "Paralela", "8584391509", "Padr??o", new Date(2019-1900,10-1,21));
		l5.setGeneros(List.of(GeneroLivro.ROMANCE));
		l5.setImagem("/livro/5/7maridosdeevelynhugo.jpg");

		Livro l6 = new Livro("Daisy Jones & The Six","Taylor Jenkins Reid", "Todo mundo conhece Daisy Jones " +
				"& The Six. Nos anos setenta, dominavam as paradas de sucesso, faziam shows para plateias lotadas e " +
				"conquistavam milh??es de f??s. Eram a voz de uma gera????o, e Daisy, a inspira????o de toda garota descolada. " +
				"Mas no dia 12 de julho de 1979, no ??ltimo show da turn?? Aurora, eles se separaram. E ningu??m nunca soube " +
				"por qu??. At?? agora. Esta ?? hist??ria de uma menina de Los Angeles que sonhava em ser uma estrela do rock " +
				"e de uma banda que tamb??m almejava seu lugar ao sol. E de tudo o que aconteceu ??? o sexo, as drogas, os " +
				"conflitos e os dramas ??? quando um produtor apostou (certo!) que juntos poderiam se tornar lendas da m??sica.",
				"PT", "14", "Paralela", "8584391401", "Padr??o", new Date(2019-1900,6-1,10));
		l6.setGeneros(List.of(GeneroLivro.ROMANCE, GeneroLivro.ROMANCE));
		l6.setImagem("/livro/6/91zeDTttzKL.jpg");

		Livro l7 = new Livro("Harry Potter e a Pedra Filosofal: 1","J.K. Rowling", "Harry Potter ?? um " +
				"garoto cujos pais, feiticeiros, foram assassinados por um poderos??ssimo bruxo quando ele ainda era um beb??. " +
				"Ele foi levado, ent??o, para a casa dos tios que nada tinham a ver com o sobrenatural. Pelo contr??rio. " +
				"At?? os 10 anos, Harry foi uma esp??cie de gata borralheira: maltratado pelos tios, herdava roupas velhas " +
				"do primo gorducho, tinha ??culos remendados e era tratado como um estorvo. No dia de seu anivers??rio de " +
				"11 anos, entretanto, ele parece deslizar por um buraco sem fundo, como o de Alice no pa??s das maravilhas, " +
				"que o conduz a um mundo m??gico. Descobre sua verdadeira hist??ria e seu destino: ser um aprendiz de feiticeiro " +
				"at?? o dia em que ter?? que enfrentar a pior for??a do mal, o homem que assassinou seus pais. O menino de " +
				"olhos verde, magricela e desengon??ado, t??o habituado ?? rejei????o, descobre, tamb??m, que ?? um her??i no " +
				"universo dos magos. Potter fica sabendo que ?? a ??nica pessoa a ter sobrevivido a um ataque do tal bruxo " +
				"do mal e essa ?? a causa da marca em forma de raio que ele carrega na testa. Ele n??o ?? um garoto qualquer, " +
				"ele sequer ?? um feiticeiro qualquer ele ?? Harry Potter, s??mbolo de poder, resist??ncia e um l??der natural " +
				"entre os sobrenaturais. A f??bula, recheada de fantasmas, paredes que falam, caldeir??es, sapos, unic??rnios," +
				" drag??es e gigantes, n??o ??, entretanto, apenas um passatempo.",
				"PT", "12", "Rocco", "8532530788", "Capa Duta", new Date(2017-1900,8-1,17));
		l7.setGeneros(List.of(GeneroLivro.FANTASIA, GeneroLivro.FICCAO_CIENTIFICA, GeneroLivro.ACAO_E_AVENTURA));
		l7.setImagem("/livro/7/hpeapedrafilosofal.jpg");

		Livro l8 = new Livro("O Homem de Giz","C. J. Tudor", "Em 1986, Eddie e os amigos passam a " +
				"maior parte dos dias andando de bicicleta pela pacata vizinhan??a em busca de aventuras. Os desenhos a giz " +
				"s??o seu c??digo secreto: homenzinhos rabiscados no asfalto; mensagens que s?? eles entendem. Mas um desenho " +
				"misterioso leva o grupo de crian??as at?? um corpo desmembrado e espalhado em um bosque. Depois disso, " +
				"nada mais ?? como antes. Em 2016, Eddie se esfor??a para superar o passado, at?? que um dia ele e os amigos " +
				"de inf??ncia recebem um mesmo aviso: o desenho de um homem de giz enforcado. Quando um dos amigos aparece " +
				"morto, Eddie tem certeza de que precisa descobrir o que de fato aconteceu trinta anos atr??s. Alternando " +
				"habilidosamente entre presente e passado, O Homem de Giz traz o melhor do suspense: personagens maravilhosamente " +
				"constru??dos, mist??rios de prender o f??lego e reviravoltas que v??o impressionar at?? os leitores mais escaldados.",
				"PT", "14", "Intr??nseca", "8551002937", "Capa Dura", new Date(2015-1900,3-1,15));
		l8.setGeneros(List.of(GeneroLivro.SUSPENSE, GeneroLivro.FICCAO_CIENTIFICA, GeneroLivro.FANTASIA));
		l8.setImagem("/livro/8/homemdegiz.jpg");

		Livro l9 = new Livro("It: A coisa","Stephen King", "Nesse cl??ssico que inspirou os filmes da Warner, " +
				"um grupo de amigos conhecido como Clube dos Ot??rios aprende o real sentido da amizade, do amor, da confian??a..." +
				" e do medo. O mais profundo e tenebroso medo. Durante as f??rias de 1958, em uma pacata cidadezinha chamada " +
				"Derry, um grupo de sete amigos come??a a ver coisas estranhas. Um conta que viu um palha??o, outro que viu " +
				"uma m??mia. Finalmente, acabam descobrindo que estavam todos vendo a mesma coisa: um ser sobrenatural e " +
				"maligno que pode assumir v??rias formas. ?? assim que Bill, Beverly, Eddie, Ben, Richie, Mike e Stan " +
				"enfrentam a Coisa pela primeira vez. Quase trinta anos depois, o grupo volta a se encontrar. Mike, o " +
				"??nico que permaneceu em Derry, d?? o sinal ??? uma nova onda de terror tomou a pequena cidade. ?? preciso " +
				"unir for??as novamente. S?? eles t??m a chave do enigma. S?? eles sabem o que se esconde nas entranhas de " +
				"Derry. S?? eles podem vencer a Coisa.",
				"PT", "16", "Suma", "8560280944", "Padr??o", new Date(2014-1900,7-1,24));
		l9.setGeneros(List.of(GeneroLivro.HORROR, GeneroLivro.SUSPENSE, GeneroLivro.DISTOPIA));
		l9.setImagem("/livro/9/itacoisa.jpg");

		Livro l10 = new Livro("Outros jeitos de usar a boca","Rupi Kaur", "Outros jeitos de usar a boca " +
				"?? um livro de poemas sobre a sobreviv??ncia. Sobre a experi??ncia de viol??ncia, o abuso, o amor, a perda e " +
				"a feminilidade. O volume ?? dividido em quatro partes, e cada uma delas serve a um prop??sito diferente. " +
				"Lida com um tipo diferente de dor. Cura uma m??goa diferente. Outros jeitos de usar a boca transporta o " +
				"leitor por uma jornada pelos momentos mais amargos da vida e encontra uma maneira de tirar delicadeza deles. " +
				"Publicado inicialmente de forma independente por Rupi Kaur, poeta, artista pl??stica e performer canadense " +
				"nascida na ??ndia e que tamb??m assina as ilustra????es presentes neste volume , o livro se tornou o maior " +
				"fen??meno do g??nero nos ??ltimos anos nos Estados Unidos, com mais de 1 milh??o de exemplares vendidos.",
				"PT", "10", "Planeta", "8542209303", "Padr??o", new Date(2017-1900,2-1,1));
		l10.setGeneros(List.of(GeneroLivro.POESIA, GeneroLivro.ROMANCE));
		l10.setImagem("/livro/10/outrosjeitosdeusaraboca.jpg");

		Livro l11 = new Livro("O pequeno pr??ncipe","Antoine de Saint-Exup??ry", "Nesta cl??ssica hist??ria " +
				"que marcou gera????es de leitores em todo o mundo, um piloto cai com seu avi??o no deserto do Saara e encontra " +
				"um pequeno pr??ncipe, que o leva a uma jornada filos??fica e po??tica atrav??s de planetas que encerram a " +
				"solid??o humana. A edi????o conta com a cl??ssica tradu????o do poeta imortal dom Marcos Barbosa, e ?? a " +
				"vers??o mais consagrada da obra, publicada no Brasil desde 1952.",
				"PT", "0", "HarperCollins", "8542209303", "Padr??o", new Date(2018-1900,8-1,27));
		l11.setGeneros(List.of(GeneroLivro.INFANTIL, GeneroLivro.ROMANCE));
		l11.setImagem("/livro/11/opequenoprincipe.jpg");

		Livro l12 = new Livro("O di??rio de Anne Frank","Anne Frank", "O di??rio de Anne Frank, o depoimento " +
				"da pequena Anne, morta pelos nazistas ap??s passar anos escondida no s??t??o de uma casa em Amsterd??, " +
				"ainda hoje emociona leitores no mundo inteiro. Suas anota????es narram os sentimentos, os medos e as " +
				"pequenas alegrias de uma menina judia que, como sua fam??lia, lutou em v??o para sobreviver ao Holocausto. " +
				"Uma poderosa lembran??a dos horrores de uma guerra, um testemunho eloquente do esp??rito humano. Assim " +
				"podemos descrever os relatos feitos por Anne em seu di??rio. Isolados do mundo exterior, os Frank enfrentaram " +
				"a fome, o t??dio e a terr??vel realidade do confinamento, al??m da amea??a constante de serem descobertos. Nas" +
				" p??ginas de seu di??rio, Anne Frank registra as impress??es sobre esse longo per??odo no esconderijo. Alternando" +
				" momentos de medo e alegria, as anota????es se mostram um fascinante relato sobre a coragem e a fraqueza humanas " +
				"e, sobretudo, um vigoroso autorretrato de uma menina sens??vel e determinada.",
				"PT", "10", "Record", "8501044458", "96??", new Date(1995-1900,9-1,29));
		l12.setGeneros(List.of(GeneroLivro.HISTORIA, GeneroLivro.BIOGRAFIA));
		l12.setImagem("/livro/12/diariodeannefrank.jpg");

		Livro l13 = new Livro("Sapiens - Uma Breve Hist??ria da Humanidade","Yuval Noah Harari", "O que " +
				"possibilitou ao Homo sapiens subjugar as demais esp??cies? O que nos torna capazes das mais belas obras de " +
				"arte, dos avan??os cient??ficos mais impens??veis e das mais horripilantes guerras? Nossa capacidade imaginativa. " +
				"Somos a ??nica esp??cie que acredita em coisas que n??o existem na natureza, como Estados, dinheiro e direitos " +
				"humanos. Partindo dessa ideia, Yuval Noah Harari, doutor em hist??ria pela Universidade de Oxford, aborda " +
				"em Sapiens a hist??ria da humanidade sob uma perspectiva inovadora. Explica que o capitalismo ?? a mais " +
				"bem-sucedida religi??o, que o imperialismo ?? o sistema pol??tico mais lucrativo, que n??s, humanos modernos, " +
				"embora sejamos muito mais poderosos que nossos ancestrais, provavelmente n??o somos mais felizes. Um relato " +
				"eletrizante sobre a aventura de nossa extraordin??ria esp??cie ? de primatas insignificantes a senhores do mundo.",
				"PT", "14", "L&PM", "8525432180", "Padr??o??", new Date(2015-1900,3-1,2));
		l13.setGeneros(List.of(GeneroLivro.HISTORIA, GeneroLivro.TECNOLOGIA_E_CIENCIA, GeneroLivro.NAO_FICCAO));
		l13.setImagem("/livro/13/umabrevehistoriasobrehmomosapiens.jpg");

		Livro l14 = new Livro("Cem dias entre c??u e mar","Amyr Klink", "Navegando ao lado dos peixes, " +
				"entretendo conversas com gaivotas e tubar??es, remando no meio de uma creche de baleias, Cem dias entre " +
				"c??u e mar ?? o relato de uma travessia absolutamente incomum: mais de 3500 milhas (cerca de 6500 quil??metros) " +
				"desde o porto de L??deritz, no sul da ??frica, at?? a praia da Espera no litoral baiano, a bordo de um " +
				"min??sculo barco a remo. Verdadeira odiss??ia moderna, neste livro Amyr Klink transporta o leitor para a " +
				"superf??cie ora cinzenta, ora azulada do Atl??ntico Sul, tornando-o c??mplice de suas alegrias e seus temores, " +
				"ao mesmo tempo em que narra, passo a passo, os preparativos, as lutas, os obst??culos e os press??gios que " +
				"cercaram a extraordin??ria viagem.",
				"PT", "0", "Companhia de bolso", "8535906428", "Padr??o", new Date(2005-1900,5-1,6));
		l14.setGeneros(List.of(GeneroLivro.VIAGEM, GeneroLivro.TECNOLOGIA_E_CIENCIA, GeneroLivro.NAO_FICCAO));
		l14.setImagem("/livro/14/cemdiasentreceuemar.jpg");

		Livro l15 = new Livro("O poder do h??bito","Charles Duhigg", "Durante os ??ltimos dois anos, " +
				"uma jovem transformou quase todos os aspectos de sua vida. Parou de fumar, correu uma maratona e foi " +
				"promovida. Em um laborat??rio, neurologistas descobriram que os padr??es dentro do c??rebro dela mudaram d" +
				"e maneira fundamental. Publicit??rios da Procter & Gamble observaram v??deos de pessoas fazendo a cama. " +
				"Tentavam desesperadamente descobrir como vender um novo produto chamado Febreze, que estava prestes a se t" +
				"ornar um dos maiores fracassos na hist??ria da empresa. De repente, um deles detecta um padr??o quase imperc" +
				"ept??vel - e, com uma sutil mudan??a na campanha publicit??ria, Febreze come??a a vender um bilh??o de d??lares p" +
				"or anos. Um diretor executivo pouco conhecido assume uma das maiores empresas norte-americanas. Seu primeiro " +
				"passo ?? atacar um ??nico padr??o entre os funcion??rios - a maneira como lidam com a seguran??a no ambiente " +
				"de trabalho -, e logo a empresa come??a a ter o melhor desempenho no ??ndice Dow Jones. O que todas" +
				" essas pessoas tem em comum? Conseguiram ter sucesso focando em padr??es que moldam cada aspecto de no" +
				"ssas vidas. Tiveram ??xito transformando h??bitos. Com perspic??cia e habilidade, Charles Duhigg apresenta um" +
				" novo entendimento da natureza humana e seu potencial para a transforma????o.",
				"PT", "0", "Objetiva", "8535906428", "Padr??o", new Date(2012-1900,9-1,24));
		l15.setGeneros(List.of(GeneroLivro.AUTO_AJUDA, GeneroLivro.NAO_FICCAO));
		l15.setImagem("/livro/15/opoderdohabito.jpg");

		Livro l16 = new Livro("Quando Ningu??m Est?? Olhando","Alyssa Cole", "Sydney Green nasceu e foi" +
				" criada no Brooklyn, em Nova York, mas cada vez que ela pisca os olhos seu amado bairro parece mudar. " +
				"Condom??nios se espalham como erva daninha, placas de ???vende-se??? surgem da noite para o dia e os vizinhos " +
				"que ela conhece a vida toda est??o sumindo. Para manter de p?? tanto o passado quanto o presente da comunidade, " +
				"Sydney decide canalizar sua frustra????o planejando um passeio guiado em que pretende contar a verdadeira " +
				"hist??ria do local. S?? que, para tornar o projeto realidade, vai precisar aturar seu novo vizinho, Theo, " +
				"como assistente. A pesquisa dos dois, entretanto, logo se transforma. O que era apenas uma distra????o " +
				"vira uma hist??ria de paranoia e medo. No fim das contas, talvez os vizinhos n??o tenham se mudado para " +
				"outros bairros e a revitaliza????o do lugar seja mais mortal do que eles imaginaram. Seriam apenas " +
				"coincid??ncias ou sinais de uma grande conspira????o? Sydney pode confiar em Theo, ou ela tamb??m corre " +
				"o risco de desaparecer? Quando ningu??m est?? olhando nos conduz por um enredo hipnotizante e surpreendente, " +
				"que aborda com perspic??cia a viol??ncia racial e as assimetrias sociais, em uma sequ??ncia de eventos " +
				"instigantes que aos poucos d??o forma a um cen??rio de completo horror.",
				"PT", "14", "Intr??nseca", "6555602090", "Padr??o", new Date(2021-1900,11-1,19));
		l16.setGeneros(List.of(GeneroLivro.JOVEM_ADULTO, GeneroLivro.SUSPENSE));
		l16.setImagem("/livro/16/quandoninguemestaolhando.jpg");

		Livro l17 = new Livro("Querido John","Sparks Nicholas", "???Querido John???, dizia a carta que partiu " +
				"um cora????o e transformou duas vidas para sempre. Quando John Tyree conhece Savannah Lynn Curtis, descobre " +
				"estar pronto para recome??ar sua vida. Com um futuro sem grandes perspectivas, ele, um jovem rebelde, decide a" +
				"listar-se no ex??rcito, ap??s concluir o ensino m??dio. Durante sua licen??a, conhece a garota de seus sonhos, " +
				"Savannah. A atra????o m??tua cresce rapidamente e logo transforma-se em um tipo de amor que faz com que Savannah p" +
				"rometa esper??-lo concluir seus deveres militares. Por??m ningu??m previa o que estava para acontecer, os at" +
				"entados de 11 de setembro mudariam suas vidas e do mundo todo. E assim como muitos homens e mulheres co" +
				"rajosos, John deveria escolher entre seu pa??s e seu amor por Savannah. Agora, quando ele finalmente retorna p" +
				"ara Carolina do Norte, ele descobre como o amor pode nos transformar de uma forma que jamais poder??amos imaginar.",
				"PT", "14", "Novo Conceito", "9788563219022", "Padr??o", new Date(2010-1900,4-1,8));
		l17.setGeneros(List.of(GeneroLivro.JOVEM_ADULTO, GeneroLivro.SUSPENSE));
		l17.setImagem("/livro/17/queridojohn.jpg");

		Livro l18 = new Livro("Uma Breve Hist??ria do Tempo","Stephen Hawking", "Marco definitivo da " +
				"literatura de divulga????o cient??fica, Uma breve hist??ria do tempo ?? relan??ado em edi????o revista e atualizada. " +
				"Uma das mentes mais geniais do mundo moderno, Stephen Hawking guia o leitor na busca por respostas a algumas das" +
				" maiores d??vidas da humanidade: Qual a origem do universo? Ele ?? infinito? E o tempo? Sempre existiu, ou" +
				" houve um come??o e haver?? um fim? Existem outras dimens??es al??m das tr??s espaciais? E o que vai acontecer " +
				"quando tudo terminar? Com ilustra????es criativas e texto l??cido e bem-humorado, Hawking desvenda desde " +
				"os mist??rios da f??sica de part??culas at?? a din??mica que movimenta centenas de milh??es de gal??xias por t" +
				"odo o universo. Para o iniciado, Uma breve hist??ria do tempo ?? uma bela representa????o de conceitos complexos; " +
				"para o leigo, ?? um vislumbre dos segredos mais profundos da cria????o.",
				"PT", "14", "Intr??nseca", "8580576466", "Padr??o", new Date(2015-1900, 0,13));
		l18.setGeneros(List.of(GeneroLivro.TECNOLOGIA_E_CIENCIA, GeneroLivro.NAO_FICCAO));
		l18.setImagem("/livro/18/umabrevehistoriadotempo.jpg");

		Livro l19 = new Livro("CAF?? COM DEUS PAI 2023","JUNIOR ROSTIROLA", "Todo pensamento gera uma" +
				" decis??o que aponta para um destino. E a nossa mente define os nossos par??metros para esse caminho." +
				" Com base nisso, para onde voc?? est?? indo? Este livro levar?? voc?? a um momento di??rio de renova????o, " +
				"pautado na Palavra de Deus, que ?? a b??ssola para uma vida bem-sucedida em todas as ??reas. Decidir por " +
				"uma mente renovada ?? decidir pela vida! Deus pai te convida para uma jornada de f??, esperan??a e renova????o" +
				". E, ao aceitar esse convite, sua vida nunca mais ser?? a mesma!",
				"PT", "12", "EDITORA VIDA\n", "6555843144", "Padr??o", new Date(2022-1900, 9,24));
		l19.setGeneros(List.of(GeneroLivro.RELIGIAO, GeneroLivro.NAO_FICCAO));
		l19.setImagem("/livro/19/cafecomdeuspai.jpg");

		//ENDERECOS USUARIOS
		Endereco e2 = new Endereco("98765432", "SC", "Blumenau", "Itoupavazinha", "Rua Arax??",
				"542", "??ltima casa da rua.");

		Endereco e3 = new Endereco("12345789", "SC", "Blumenau", "Jardim Blumenau", "Rua Arboria",
				"14", "Ao lado da farm??cia");

		Endereco e4 = new Endereco("98765432", "SC", "Blumenau", "Velha Central", "Rua Ca??adores",
				"87", "Apto 201");

		Endereco e5 = new Endereco("98765432", "SC", "Blumenau", "Garcia", "Rua Amazonas",
				"412", "Em frente ao batalh??o.");

		//CADASTRO DE USUARIOS
		Usuario u1 = new Usuario("581.780.119-13", "Maria Clara", "Peron", "mariaclara@email.com",
				"47521452145", null, TipoPerfil.LEITOR, true, new Date(2005-1900,2-1,19));
		u1.setEnderecoUsuario(e2);
		u1.setBiblioteca(b1);
		u1.setImagem("/usuario/100001/images(1).jpeg");
		u1.setId(100001L);

		Usuario u2 = new Usuario("347.497.177-89", "Veridiana", "Berti", "veriberti@email.com",
				"47569852145", null, TipoPerfil.LEITOR,true, new Date(2005-1900,5-1,2));
		u2.setEnderecoUsuario(e3);
		u2.setBiblioteca(b1);
		u2.setImagem("/usuario/100002/images.png");
		u2.setId(100002L);

		Usuario u3 = new Usuario("388.985.320-08", "Lucas", "Testoni", "testoni@email.com",
				"47854124785", "4763215896", TipoPerfil.LEITOR, true, new Date(2004-1900, 0,1));
		u3.setEnderecoUsuario(e4);
		u3.setBiblioteca(b1);
		u3.setImagem("/usuario/100003/download.jpg");
		u3.setId(100003L);

		Usuario u4 = new Usuario("784.736.017-93", "Jeniffer", "Cristina", "jeniffer@email.com",
				"47552362145", "47258712587", TipoPerfil.LEITOR, true, new Date(2004-1900,8-1,15));
		u4.setEnderecoUsuario(e5);
		u4.setBiblioteca(b1);
		u4.setImagem("/usuario/100004/images(1).jpeg");
		u4.setId(100004L);

		//CADASTRO DE LIVROS DA BIBLIOTECA
		LivroBiblioteca lb1 = new LivroBiblioteca(l1, b1, 5);
		LivroBiblioteca lb2 = new LivroBiblioteca(l2, b1, 5);
		LivroBiblioteca lb3 = new LivroBiblioteca(l3, b1, 5);
		LivroBiblioteca lb4 = new LivroBiblioteca(l4, b1, 5);
		LivroBiblioteca lb5 = new LivroBiblioteca(l5, b1, 5);
		LivroBiblioteca lb6 = new LivroBiblioteca(l6, b1, 5);
		LivroBiblioteca lb7 = new LivroBiblioteca(l7, b1, 5);
		LivroBiblioteca lb8 = new LivroBiblioteca(l8, b1, 5);
		LivroBiblioteca lb9 = new LivroBiblioteca(l9, b1, 5);
		LivroBiblioteca lb10 = new LivroBiblioteca(l10, b1, 5);
		LivroBiblioteca lb11 = new LivroBiblioteca(l11, b1, 5);
		LivroBiblioteca lb12 = new LivroBiblioteca(l12, b1, 5);
		LivroBiblioteca lb13 = new LivroBiblioteca(l13, b1, 5);
		LivroBiblioteca lb14 = new LivroBiblioteca(l14, b1, 5);
		LivroBiblioteca lb15 = new LivroBiblioteca(l15, b1, 5);
		LivroBiblioteca lb16 = new LivroBiblioteca(l16, b1, 5);
		LivroBiblioteca lb17 = new LivroBiblioteca(l17, b1, 5);
		LivroBiblioteca lb18 = new LivroBiblioteca(l18, b1, 5);
		LivroBiblioteca lb19 = new LivroBiblioteca(l19, b1, 5);

		//CDASTRO DE LOCACOES
		Locacao loc1 = new Locacao(new Date(2022-1900,5-1,20), new Date(2022-1900,8-1,19),
				StatusLocacao.DEVOLVIDO);
		loc1.setLivro(lb1);
		loc1.setDataDevolvida(new Date(2022-1900,8-1,19));
		loc1.setUsuario(u2);

		Locacao loc2 = new Locacao(new Date(2022-1900,10-1,29), new Date(2022-1900,11-1,29),
				StatusLocacao.ANDAMENTO);
		loc2.setLivro(lb3);
		loc2.setUsuario(u1);

		Locacao loc3 = new Locacao(new Date(2022-1900,10-1,30), new Date(2022-1900,11-1,30),
				StatusLocacao.ANDAMENTO);
		loc3.setLivro(lb5);
		loc3.setUsuario(u2);

		Locacao loc4 = new Locacao(new Date(2022-1900,9-1,14), new Date(2022-1900,10-1,14),
				StatusLocacao.PENDENTE);
		loc4.setLivro(lb6);
		loc4.setUsuario(u3);

		Locacao loc5 = new Locacao(new Date(2022-1900,9-1,20), new Date(2022-1900,11-1,20),
				StatusLocacao.ANDAMENTO);
		loc5.setLivro(lb3);
		loc5.setUsuario(u4);

		Locacao loc6 = new Locacao(new Date(2022-1900,7-1,20), new Date(2022-1900,8-1,20),
				StatusLocacao.DEVOLVIDO);
		loc6.setLivro(lb3);
		loc6.setDataDevolvida(new Date(2022-1900,8-1,19));
		loc6.setUsuario(u4);

		//CADASTRO DE RESENHAS
		Resenha r1 = new Resenha("Muito bom");
		r1.setLivro(l1);
		r1.setUsuario(u1);

		Resenha r2 = new Resenha("Maravilhosoo");
		r2.setLivro(l1);
		r2.setUsuario(u2);

		Resenha r3 = new Resenha("Odiei");
		r3.setLivro(l1);
		r3.setUsuario(u4);

		Resenha r4 = new Resenha("??timo");
		r4.setLivro(l2);
		r4.setUsuario(u2);

		Resenha r5 = new Resenha("N??o curti");
		r5.setLivro(l2);
		r5.setUsuario(u1);

		Resenha r6 = new Resenha("Gostei da escrita");
		r6.setLivro(l2);
		r6.setUsuario(u3);

		Resenha r7 = new Resenha("Achei mais ou menos");
		r7.setLivro(l3);
		r7.setUsuario(u3);

		Resenha r8 = new Resenha("Muuuito bom");
		r8.setLivro(l3);
		r8.setUsuario(u2);

		Resenha r9 = new Resenha("Perfeito!");
		r9.setLivro(l3);
		r9.setUsuario(u1);

		Resenha r10 = new Resenha("Sem opini??es");
		r10.setLivro(l4);
		r10.setUsuario(u4);

		Resenha r11 = new Resenha("Chorei muito");
		r11.setLivro(l5);
		r11.setUsuario(u2);

		Resenha r12 = new Resenha("Chorei muito");
		r12.setLivro(l5);
		r12.setUsuario(u1);

		Resenha r13 = new Resenha("Esperava mais");
		r13.setLivro(l6);
		r13.setUsuario(u3);

		Notificacao n1 = new Notificacao(null, b1, "Livro devolvido",
				"Livro "+ loc1.getLivro().getLivro().getNome() +" locacado pelo usu??rio "+
				loc1.getUsuario().getNome() +" "+ loc1.getUsuario().getSobrenome() +" devolvido.");

		Notificacao n2 = new Notificacao(null, b1, "Livro devolvido",
				"Livro "+ loc6.getLivro().getLivro().getNome() +" locacado pelo usu??rio "+
						loc6.getUsuario().getNome() +" "+ loc6.getUsuario().getSobrenome() +" devolvido.");

		Notificacao n3 = new Notificacao(null, b1, "Pend??ncia",
				"Usu??rio "+ loc4.getUsuario().getId() +" - "+ loc4.getUsuario().getNome() +" "+
						loc4.getUsuario().getSobrenome()+ " possui uma p??ndencia na loca????o "+ loc4.getId());

		Notificacao n4 = new Notificacao(null, b1, "Livro locado",
				"Usu??rio "+ loc5.getUsuario().getId() +" - "+ loc5.getUsuario().getNome() +" "+
						loc5.getUsuario().getSobrenome()+ " locou o livro "+ loc5.getLivro().getLivro().getNome()
						+" no dia "+ loc5.getDataLocacao());

		Notificacao n5 = new Notificacao(null, b1, "Livro locado",
				"Usu??rio "+ loc2.getUsuario().getId() +" - "+ loc2.getUsuario().getNome() +" "+
						loc2.getUsuario().getSobrenome()+ " locou o livro "+ loc2.getLivro().getLivro().getNome()
						+" no dia "+ loc2.getDataLocacao());

		Notificacao n6 = new Notificacao(null, b1, "Livro locado",
				"Usu??rio "+ loc3.getUsuario().getId() +" - "+ loc3.getUsuario().getNome() +" "+
						loc3.getUsuario().getSobrenome()+ " locou o livro "+ loc3.getLivro().getLivro().getNome()
						+" no dia "+ loc3.getDataLocacao());

		//SALVANDO NO BANCO
		enderecoRepository.saveAll(List.of(e1, e2, e3, e4, e5));
		bibliotecaRepository.save(b1);
		livroRepository.saveAll(List.of(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19));
		usuarioRepository.saveAll(List.of(u1, u2, u3, u4));
		resenhaRepository.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7 ,r8, r9, r10, r11, r12, r13));
		livroBibliotecaRepository.saveAll(List.of(lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9, lb10, lb11, lb12, lb13, lb14, lb15, lb16, lb17, lb18, lb19));
		locacaoRepository.saveAll(List.of(loc1, loc2, loc3, loc4, loc5, loc6));
		notificacaoRepository.saveAll(List.of(n1, n2, n3, n4, n5, n6));
	}

}
