'use strict';

angular.module('tiimspotApp')
    .factory('ContactinfoSearch', function ($resource) {
        return $resource('api/_search/contactinfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
