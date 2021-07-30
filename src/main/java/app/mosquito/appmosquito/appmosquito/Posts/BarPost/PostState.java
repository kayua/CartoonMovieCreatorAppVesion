package app.mosquito.appmosquito.appmosquito.Posts.BarPost;


public enum PostState {

    FAZENDO {
        @Override
        public String toString() {
            return "fazendo";
        }
    }, FINALIZADO {
        @Override
        public String toString() {
            return "finalizado";
        }
    }
}
