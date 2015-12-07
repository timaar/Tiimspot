'use strict';

angular.module('tiimspotApp')
	.controller('AdresDeleteController', function($scope, $modalInstance, entity, Adres) {

        $scope.adres = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Adres.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });